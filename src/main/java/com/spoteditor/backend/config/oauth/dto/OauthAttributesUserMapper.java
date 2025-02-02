package com.spoteditor.backend.config.oauth.dto;

import com.spoteditor.backend.user.entity.User;
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