package com.authapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * SecurityConfig
 *
 * <p>Central Spring Security setup for the application:
 *
 * <p>• Disables CSRF protection (for a stateless JWT API). • Permits unrestricted access to: –
 * /api/signup, /api/login (auth endpoints) – /api/hello, /api/goodbye (public greetings) –
 * /api/pokemon/** (public Pokémon endpoints) • Secures /api/account and all other endpoints behind
 * authentication. • Registers JwtAuthFilter before the UsernamePasswordAuthenticationFilter to
 * enforce JWT-based auth on protected routes. • Disables HTTP Basic and form-based login.
 */
@Configuration
public class SecurityConfig {

  private final JwtAuthFilter jwtAuthFilter;

  public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
    this.jwtAuthFilter = jwtAuthFilter;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers(
                        "/api/signup",
                        "/api/login",
                        "/api/hello",
                        "/api/goodbye",
                        "/api/pokemon/**")
                    .permitAll()
                    .requestMatchers("/api/friends/**", "/api/account")
                    .authenticated()
                    .anyRequest()
                    .authenticated())
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        .httpBasic(httpBasic -> httpBasic.disable())
        .formLogin(formLogin -> formLogin.disable());

    return http.build();
  }
}
