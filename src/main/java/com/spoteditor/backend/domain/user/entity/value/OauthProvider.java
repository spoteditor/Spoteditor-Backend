package com.spoteditor.backend.domain.user.entity.value;

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
        throw new UnsupportedOperationException("지원하지 않는 provider: " + registrationId);
    }
}
