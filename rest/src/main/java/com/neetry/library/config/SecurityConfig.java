package com.neetry.library.config;

import com.neetry.library.auth.filter.JwtAuthenticationFilter;
import com.neetry.library.user.auth.jwt.service.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static com.neetry.library.user.auth.model.Permissions.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private static final String[] WHITE_LIST_URL = {};
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final JwtService jwtService;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter, AuthenticationProvider authenticationProvider, JwtService jwtService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
        this.jwtService = jwtService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            LogoutHandler logoutHandler
    ) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                                req.requestMatchers(WHITE_LIST_URL)
                                        .permitAll()
//                                Book Routes
                                        .requestMatchers(GET, "/api/books/**").hasAnyAuthority(SEARCH_BOOKS)
                                        .requestMatchers(POST, "/api/books/**").hasAnyAuthority(MANAGE_BOOKS)
                                        .requestMatchers(DELETE, "/api/books/**").hasAnyAuthority(MANAGE_BOOKS)
//                                  Management Routes
                                        .requestMatchers(PATCH, "/api/management/**").hasAnyAuthority(GRANT_ROLES)
                                        .anyRequest()
                                        .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                )
        ;

        return http.build();
    }

    @Bean
    public LogoutHandler logoutHandler() {
        return (request, response, authentication) -> {
            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return;
            }
            jwt = authHeader.substring(7);
            jwtService.setExpired(jwt);
        };
    }
}
