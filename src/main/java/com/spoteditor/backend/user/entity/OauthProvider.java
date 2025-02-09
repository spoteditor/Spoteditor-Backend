package com.spoteditor.backend.user.entity;

import com.spoteditor.backend.global.exception.UserException;

import static com.spoteditor.backend.global.response.ErrorCode.UNSUPPORTED_PROVIDER;

public enum OauthProvider {
    KAKAO("kakao");

    private final String provider;

    OauthProvider(String provider) {
        this.provider = provider;
    }

    public String getProvider() {
        return provider;
    }

    public static OauthProvider from(String registrationId) {
        for (OauthProvider provider : OauthProvider.values()) {
            if (provider.getProvider().equalsIgnoreCase(registrationId)) {
                return provider;
            }
        }
        throw new UserException(UNSUPPORTED_PROVIDER);
    }
}
