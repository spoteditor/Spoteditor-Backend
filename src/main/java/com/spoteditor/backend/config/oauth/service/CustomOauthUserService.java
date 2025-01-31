package com.spoteditor.backend.config.oauth.service;

import com.spoteditor.backend.config.oauth.dto.OauthAttributes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOauthUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final OauthUserResolver oauthUserResolver;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        OauthAttributes attributes = getOAuthAttributes(userRequest, oAuth2User);

        Long createdUserId = oauthUserResolver.resolveUserId(attributes);
        Map<String, Object> userIdMap = Map.of("id", createdUserId);

        return new DefaultOAuth2User(
                Collections.emptySet(),
                userIdMap,
                "id"
        );
    }

    public OauthAttributes getOAuthAttributes(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        
        return OauthAttributes.of(registrationId, oAuth2User.getAttributes());
    }
}
