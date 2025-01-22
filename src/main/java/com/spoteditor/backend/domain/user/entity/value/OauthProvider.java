package com.spoteditor.backend.domain.user.entity.value;

import com.spoteditor.backend.common.exceptions.BaseException;
import com.spoteditor.backend.common.exceptions.ErrorCode;

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
        throw new BaseException(ErrorCode.UNSUPPORTED_PROVIDER);
    }
}
