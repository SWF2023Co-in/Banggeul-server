package coin.banggeul.auth.service;

import coin.banggeul.auth.AuthTokenProvider;
import coin.banggeul.auth.domain.*;
import coin.banggeul.auth.exception.AuthErrorCode;
import coin.banggeul.auth.exception.AuthException;
import coin.banggeul.auth.exception.ProviderErrorCode;
import coin.banggeul.auth.exception.ProviderException;
import coin.banggeul.common.AppProperties;
import coin.banggeul.common.utils.JsonUtil;
import coin.banggeul.member.domain.Member;
import coin.banggeul.member.domain.MemberRepository;
import coin.banggeul.member.domain.Provider;
import coin.banggeul.member.domain.Role;
import coin.banggeul.member.dto.AuthInfo;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoService implements LoginService {

    private final MemberRepository memberRepository;

    private final RefreshTokenRepository refreshTokenRepository;
    private final RestTemplate restTemplate;
    private final Gson gson;
    private final AuthenticationManager authenticationManager;
    private final AuthTokenProvider tokenProvider;
    private final AppProperties appProperties;


    @Value("${kakao.client-id}")
    private String clientId;
    @Value("${kakao.redirect-uri}")
    private String redirectUri;
    @Value("${kakao.password}")
    private String kakaoPassword;

    @Transactional
    @Override
    public AuthInfo login(String code) {

        String url = "https://kauth.kakao.com/oauth/token";
        HttpEntity<MultiValueMap<String, String>> request = makeRequestForToken(code);
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                Map mapForToken = gson.fromJson(response.getBody(), HashMap.class);
                log.info("액세스 토큰 받기 성공");
                AuthInfo authInfo = getAccessTokenWithKakaoProfile((String) mapForToken.get("access_token"));
                return authInfo;
            }
            throw new ProviderException(ProviderErrorCode.KAKAO, "액세스 토큰 받기 실패");
        } catch (Exception e) {
            log.error(e.toString());
            throw new ProviderException(ProviderErrorCode.KAKAO);
        }
    }

    private HttpEntity<MultiValueMap<String, String>> makeRequestForToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-type" , "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        return request;
    }

    private AuthInfo getAccessTokenWithKakaoProfile(String accessToken) {
        String url = "https://kapi.kakao.com/v2/user/me";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-type" , "application/x-www-form-urlencoded;charset=utf-8");
        headers.set("Authorization", "Bearer " + accessToken);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> profile = JsonUtil.jsonToMap(response.getBody());
                String username = Provider.KAKAO.getName() + profile.get("id");
                if (memberRepository.findByUsername(username).isEmpty()) {
                    saveMember(profile);
                }
                String token = getAuthentication(username);

                // 리프레시 토큰 발급 후 저장
                Date now = new Date();
                AuthToken refreshToken = tokenProvider.createAuthToken(
                        appProperties.getAuth().getTokenSecret(),
                        new Date(now.getTime() + appProperties.getAuth().getRefreshTokenExpiry())
                );

                //username refresh token 으로 DB 확인
                RefreshToken memberRefreshToken = refreshTokenRepository.findByUsername(username);
                if (memberRefreshToken == null) {
                    // 없으면 새로 등록
                    memberRefreshToken = new RefreshToken(username, refreshToken.getToken());
                    refreshTokenRepository.save(memberRefreshToken);
                } else {
                    // DB에 refresh token 업데이트
                    memberRefreshToken.setRefreshToken(refreshToken.getToken());
                }
                return new AuthInfo(token, memberRefreshToken.getRefreshToken());
            }
            throw new ProviderException(ProviderErrorCode.KAKAO, "카카오 프로필 받기 실패");
        } catch (Exception e) {
            log.error(e.toString());
            throw new ProviderException(ProviderErrorCode.KAKAO);
        }
    }

    private void saveMember(Map profile) {
        Map<String, Map<String, String>> map = profile;
        log.info("회원 닉네임: "+ map.get("properties").get("nickname"));
        String username = Provider.KAKAO.getName() + map.get("id");
        Member newMember = Member.builder()
                .username(username)
                .password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(kakaoPassword))
                .name(map.get("properties").get("nickname"))
                .provider(Provider.KAKAO)
                .role(Role.USER)
                .build();
        memberRepository.save(newMember);
    }

    public String getAuthentication(String username) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username,
                            kakaoPassword
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            Date now = new Date();

            AuthToken accessToken = tokenProvider.createAuthToken(
                    username,
                    ((CustomUserDetails) authentication.getPrincipal()).getRoleName(),
                    new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
            );
            return accessToken.getToken();
        } catch (BadCredentialsException e) {
            throw new AuthException(AuthErrorCode.CREDENTIAL_MISS_MATCH);
        }
    }
}
