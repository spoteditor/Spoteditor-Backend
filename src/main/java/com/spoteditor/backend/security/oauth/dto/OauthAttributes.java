package com.spoteditor.backend.security.oauth.dto;

import com.spoteditor.backend.common.exceptions.user.UserException;
import com.spoteditor.backend.common.exceptions.user.UserErrorCode;
import com.spoteditor.backend.domain.user.entity.value.OauthProvider;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OauthAttributes {

    private final String oauthUserId;
    private final String name;
    private final String imageUrl;
    private final String email;
    private final OauthProvider provider;

    @Builder
    public OauthAttributes(
            String oauthUserId, String name, String imageUrl, String email, OauthProvider provider
    ) {
        this.oauthUserId = oauthUserId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.email = email;
        this.provider = provider;
    }

    public static OauthAttributes of (
            String registrationId, Map<String, Object> attributes
    ) {
        OauthProvider provider = OauthProvider.from(registrationId);

        switch (provider) {
            case KAKAO:
                return ofKakao(attributes, provider);
            default:
                throw new UserException(UserErrorCode.UNSUPPORTED_PROVIDER);
        }
    }

    private static OauthAttributes ofKakao (
            Map<String, Object> attributes, OauthProvider provider
    ) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");

        String oauthUserId = String.valueOf(attributes.get("id"));
        String name = (String) properties.get("nickname");
        String imageUrl = (String) properties.get("profile_image");
        String email = (String) kakaoAccount.get("email");

        return OauthAttributes.builder()
                .oauthUserId(oauthUserId)
                .name(name)
                .imageUrl(imageUrl)
                .email(email)
                .provider(provider)
                .build();
    }

}
