package coin.banggeul.auth;

import coin.banggeul.auth.domain.AuthToken;
import coin.banggeul.auth.domain.RefreshToken;
import coin.banggeul.auth.exception.AuthException;
import coin.banggeul.common.utils.CookieUtil;
import coin.banggeul.common.utils.HeaderUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class TokenAuthorizationFilter extends OncePerRequestFilter {
// 토큰 검증하는 필터
    private final AuthTokenProvider tokenProvider;

    private static final List<String> EXCLUDE_URL =
            List.of(
                    "/health",
                    "/login/kakao",
                    "/reissue",
                    "/properties/entire",
                    "/properties/info"
            );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String stringToken = HeaderUtil.getAccessToken(request);
        AuthToken token = tokenProvider.convertAuthToken(stringToken);
        log.info("TokenAuthenticationFilter works");

        if (token.validate()) {
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return EXCLUDE_URL.stream().anyMatch(exclude -> exclude.equalsIgnoreCase(request.getServletPath()));
    }


}
