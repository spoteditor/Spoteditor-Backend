package com.spoteditor.backend.security.util;

import com.spoteditor.backend.common.exceptions.user.UserErrorCode;
import com.spoteditor.backend.common.exceptions.user.UserException;
import com.spoteditor.backend.domain.user.common.dto.UserIdDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    public static Long getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null) {
            throw new UserException(UserErrorCode.NO_AUTHENTICATED_USER);
        }

        return ((UserIdDto) authentication.getPrincipal()).getId();
    }
}
