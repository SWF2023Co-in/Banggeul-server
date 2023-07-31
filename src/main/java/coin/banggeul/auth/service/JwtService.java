package coin.banggeul.auth.service;

import coin.banggeul.auth.AuthTokenProvider;
import coin.banggeul.auth.domain.AuthToken;
import coin.banggeul.auth.domain.RefreshToken;
import coin.banggeul.auth.domain.RefreshTokenRepository;
import coin.banggeul.auth.exception.AuthErrorCode;
import coin.banggeul.auth.exception.AuthException;
import coin.banggeul.common.AppProperties;
import coin.banggeul.member.domain.Role;
import coin.banggeul.member.dto.AuthInfo;
import coin.banggeul.member.dto.TokenDto;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthTokenProvider tokenProvider;
    private final AppProperties appProperties;

    private final static long THREE_DAYS_MSEC = 259200000;
    private final static String REFRESH_TOKEN = "refresh_token";

    @Transactional
    public AuthInfo reissue(TokenDto dto) {
        AuthToken expiredToken = tokenProvider.convertAuthToken(dto.getAccessToken());
        AuthToken refreshToken = tokenProvider.convertAuthToken(dto.getRefreshToken());

        Claims claims = expiredToken.getExpiredTokenClaims();
        if (claims == null) {
            throw new AuthException(AuthErrorCode.NOT_EXPIRED_TOKEN_YET);
        } else {
            log.info("claims={}", claims);
        }
        String username = claims.getSubject();
        Role role = Role.of(claims.get("role", String.class));

        if (!refreshToken.validate()) {
            throw new AuthException(AuthErrorCode.INVALID_REFRESH_TOKEN);
        }

        // refresh token으로 DB에서 user 정보와 확인
        RefreshToken memberRefreshToken = refreshTokenRepository.findByUsernameAndRefreshToken(username, dto.getRefreshToken());
        log.info("UserRefreshToken={}", refreshToken);
        if (memberRefreshToken == null) {
            throw new AuthException(
                    AuthErrorCode.INVALID_REFRESH_TOKEN,
                    "가입되지 않은 회원이거나 유효하지 않은 리프레시 토큰입니다."
            );
        }

        Date now = new Date();

        AuthToken newAccessToken = tokenProvider.createAuthToken(
                username,
                role.getCode(),
                new Date(now.getTime() + appProperties.getAuth().getTokenExpiry())
        );

        long validTime = refreshToken.getTokenClaims().getExpiration().getTime() - now.getTime();

        // 리프레시 토큰 만료기간이 3일 이하인 경우 refresh token 발급
        if (validTime <= THREE_DAYS_MSEC) {
            long refreshTokenExpiry = appProperties.getAuth().getRefreshTokenExpiry();

            refreshToken = tokenProvider.createAuthToken(
                    appProperties.getAuth().getTokenSecret(),
                    new Date(now.getTime() + refreshTokenExpiry)
            );
            // DB에 토큰 업데이트
            memberRefreshToken.setRefreshToken(refreshToken.getToken());
        }

        return new AuthInfo(newAccessToken.getToken(), memberRefreshToken.getRefreshToken());
    }

    @Transactional
    public String checkToken(String accessToken) {
        return tokenProvider.convertAuthToken(accessToken).getTokenClaims().getSubject();
    }
}
