package com.spoteditor.backend.config.util;

import com.spoteditor.backend.global.exception.UserException;
import com.spoteditor.backend.user.common.dto.UserTokenDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static com.spoteditor.backend.global.response.ErrorCode.NO_AUTHENTICATED_USER;

@Component
public class SecurityUtils {

    public static Long getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new UserException(NO_AUTHENTICATED_USER);
        }

        return ((UserTokenDto) authentication.getPrincipal()).getId();
    }
}
