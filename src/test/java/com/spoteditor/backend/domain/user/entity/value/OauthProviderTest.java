package com.spoteditor.backend.domain.user.entity.value;

import com.spoteditor.backend.global.exception.UserException;
import com.spoteditor.backend.user.entity.OauthProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("OauthProvider Enum 테스트")
class OauthProviderTest {

    @Test
    @DisplayName("지원하는 provider(Kakao) 확인")
    public void 지원하는_Provider_테스트() throws Exception {
        // given
        String registrationId = "Kakao";

        // when
        OauthProvider provider = OauthProvider.from(registrationId);

        // then
        Assertions.assertThat(provider).isEqualTo(OauthProvider.KAKAO);
    }

    @Test
    @DisplayName("지원하지 않는 provider(Naver) 예외")
    public void 지원하지_않는_Provider_오류_테스트() throws Exception {
        // given
        String registrationId = "Naver";

        // when
        UserException exception = assertThrows(UserException.class, () -> {
            OauthProvider.from(registrationId);
        });

        // then
        Assertions.assertThat(exception.getMessage()).isEqualTo("지원하지 않는 Provider");
    }
}