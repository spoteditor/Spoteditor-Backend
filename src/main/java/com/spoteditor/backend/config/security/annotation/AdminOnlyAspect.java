package com.spoteditor.backend.config.security.annotation;

import com.spoteditor.backend.global.exception.UserException;
import com.spoteditor.backend.global.response.ErrorCode;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.security.core.GrantedAuthority;

import static com.spoteditor.backend.global.response.ErrorCode.NEED_ADMIN_ROLE;

@Aspect
@Component
public class AdminOnlyAspect {

    @Before("@annotation(com.spoteditor.backend.config.security.annotation.AdminOnly)")
    public void checkAdminAccess(JoinPoint joinPoint) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getAuthorities() == null) {
            throw new UserException(ErrorCode.NO_AUTHENTICATED_USER);
        }

        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        if (!isAdmin) {
            throw new UserException(NEED_ADMIN_ROLE);
        }
    }
}
