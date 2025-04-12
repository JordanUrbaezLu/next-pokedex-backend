package com.authapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // 🔥 This is what fixes the 403
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/signup", "/api/login", "/api/hello").permitAll()
                .anyRequest().authenticated()
            )
            .httpBasic().disable()
            .formLogin().disable();

        return http.build();
    }
}

