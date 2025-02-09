package com.spoteditor.backend.config.security;

import com.spoteditor.backend.config.util.CookieUtils;
import com.spoteditor.backend.config.jwt.JwtFilter;
import com.spoteditor.backend.config.jwt.JwtUtils;
import com.spoteditor.backend.config.oauth.handler.OauthFailureHandler;
import com.spoteditor.backend.config.oauth.handler.OauthSuccessHandler;
import com.spoteditor.backend.config.oauth.service.CustomOauthUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOauthUserService customOauthUserService;
    private final OauthSuccessHandler oauthSuccessHandler;
    private final OauthFailureHandler oauthFailureHandler;

    private final JwtUtils jwtUtils;
    private final CookieUtils cookieUtils;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .formLogin(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(sessionManagement ->
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(new JwtFilter(jwtUtils, cookieUtils), UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/**").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfoEndpointConfig
                    -> userInfoEndpointConfig.userService(customOauthUserService))
                .successHandler(oauthSuccessHandler)
                .failureHandler(oauthFailureHandler)
            );

        return http.build();
    }
}
