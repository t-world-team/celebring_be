package com.tworld.celebring.security.config;

import com.tworld.celebring.login.service.LoginService;
import com.tworld.celebring.security.token.TokenFilter;
import com.tworld.celebring.security.token.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final LoginService loginService;
    private final TokenProvider tokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();

        // 로그인 oauth2
        http.oauth2Login()
                .userInfoEndpoint()
                .userService(loginService);

        // 토큰 필터
        http.addFilterBefore(new TokenFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);

        // 세션 미사용
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }

}
