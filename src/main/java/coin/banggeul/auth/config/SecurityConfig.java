package coin.banggeul.auth.config;

import coin.banggeul.auth.AuthTokenProvider;
import coin.banggeul.auth.service.CustomUserDetailsService;
import coin.banggeul.auth.TokenAuthorizationFilter;
import coin.banggeul.auth.exception.AuthExceptionFilter;
import coin.banggeul.common.response.ErrorEntity;
import coin.banggeul.common.response.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final AuthTokenProvider tokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public TokenAuthorizationFilter tokenAuthenticationFilter() {
        return new TokenAuthorizationFilter(tokenProvider);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.headers(configurer -> configurer.frameOptions(FrameOptionsConfig::disable));
        http.csrf(CsrfConfigurer::disable);
        http.cors(
                configurer -> configurer.configurationSource(configurationSource())
        );

        http.sessionManagement(
                configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
        http.formLogin(Customizer.withDefaults());
        http.httpBasic(HttpBasicConfigurer::disable);

        http.exceptionHandling(
                authenticationManager -> authenticationManager.authenticationEntryPoint(
                        (request, response, authException) -> {
                            ResponseUtil.error(new ErrorEntity("AUTHENTICATION_ENTRY_POINT", "인증 실패"));
                        }
                )
        );

        http.authorizeHttpRequests(
                authorize -> authorize.requestMatchers(antMatcher("/health")).permitAll()
                        .requestMatchers(antMatcher("/login/**")).permitAll()
                        .requestMatchers(HttpMethod.GET, "/properties/entire").permitAll()
                        .requestMatchers(HttpMethod.GET, "/properties/info").permitAll()
                        .requestMatchers(HttpMethod.GET, "/reissue").permitAll()
                        .anyRequest().authenticated()
        );

        http.addFilterBefore(new TokenAuthorizationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new AuthExceptionFilter(), TokenAuthorizationFilter.class);

        http.authenticationProvider(authenticationProvider());

        return http.build();
    }

    public CorsConfigurationSource configurationSource() {
        log.info("debug: configurationSource cors settings are registered in FilterChain bean.");
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedOriginPattern("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
