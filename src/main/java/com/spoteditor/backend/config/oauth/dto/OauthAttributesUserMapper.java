package com.spoteditor.backend.config.oauth.dto;

import com.spoteditor.backend.user.entity.User;
import com.spoteditor.backend.user.entity.UserRole;
import org.springframework.stereotype.Component;

@Component
public class OauthAttributesUserMapper {

    public User toEntity(OauthAttributes attributes) {
        return User.builder()
                .email(attributes.getEmail())
                .name(attributes.getName())
                .imageUrl(attributes.getImageUrl())
                .provider(attributes.getProvider())
                .oauthUserId(attributes.getOauthUserId())
                .role(UserRole.USER)
                .build();
    }
}