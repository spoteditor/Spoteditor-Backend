package com.spoteditor.backend.security.oauth.dto;

import com.spoteditor.backend.domain.user.entity.User;
import com.spoteditor.backend.domain.user.entity.value.OauthProvider;
import org.springframework.stereotype.Component;

@Component
public class OauthAttributesUserMapper {

    public User toEntity(OauthAttributes attributes) {
        return User.builder()
                .oauthUserId(attributes.getOauthUserId())
                .name(attributes.getName())
                .imageUrl(attributes.getImageUrl())
                .email(attributes.getEmail())
                .provider(attributes.getProvider())
                .build();
    }
}