
package com.rebook.rebook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
            // Keep the existing custom login and logout API.
            .csrf(AbstractHttpConfigurer::disable)

            // Use our own session-based login in AuthController.
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)

            .authorizeHttpRequests(auth -> auth
                // Public pages and authentication endpoints.
                .requestMatchers(
                    "/",
                    "/login.html",
                    "/register.html",
                    "/style.css",
                    "/api/auth/**",
                    "/api/items/**"
                ).permitAll()

                // Profile data is checked by our session-based controller.
                .requestMatchers("/api/profile").permitAll()

                // Keep the other existing pages accessible for now.
                .anyRequest().permitAll()
            );

        return http.build();
    }
}

