package com.spoteditor.backend.domain.user.entity.value;

public enum OauthProvider {
    KAKAO("kakao");

    private final String provider;

    OauthProvider(String provider) {
        this.provider = provider;
    }
}
