package com.tworld.celebring.security.config;

import com.tworld.celebring.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final LoginService loginService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();

        // 로그인 oauth2
        http.oauth2Login()
                .userInfoEndpoint()
                .userService(loginService);

        return http.build();
    }

}
