package com.leavetracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/api/**").permitAll()
                .requestMatchers("/static/**", "/", "/index.html", "/**/*.js", "/**/*.css", "/**/*.html").permitAll()
                .anyRequest().permitAll()
            )
            .httpBasic().disable()
            .formLogin().disable();
        return http.build();
    }
}
