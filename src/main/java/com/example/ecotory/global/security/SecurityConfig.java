package com.example.ecotory.global.security;

import com.example.ecotory.global.security.jwt.JWTVerifyFilter;
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

    // JWTVerifyFilter를 직접 Bean으로 등록
    @Bean
    public JWTVerifyFilter jwtVerifyFilter() {
        return new JWTVerifyFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   JWTVerifyFilter jwtVerifyFilter) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/member/**").permitAll()
                        .requestMatchers("/api/market/**").permitAll()
                        .requestMatchers("/api/asset/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtVerifyFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}