package com.spoteditor.backend.security.config;

import com.spoteditor.backend.security.jwt.JwtFilter;
import com.spoteditor.backend.security.jwt.JwtProvider;
import com.spoteditor.backend.security.oauth.handler.OauthFailureHandler;
import com.spoteditor.backend.security.oauth.handler.OauthSuccessHandler;
import com.spoteditor.backend.security.oauth.service.CustomOauthUserService;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class SecurityConfig {

    private final CustomOauthUserService customOauthUserService;
    private final OauthSuccessHandler oauthSuccessHandler;
    private final OauthFailureHandler oauthFailureHandler;
    private final JwtProvider jwtProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .formLogin(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(sessionManagement ->
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(new JwtFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
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
