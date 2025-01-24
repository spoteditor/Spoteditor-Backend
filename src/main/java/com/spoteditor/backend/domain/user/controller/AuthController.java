package com.spoteditor.backend.domain.user.controller;

import com.spoteditor.backend.common.exceptions.BaseException;
import com.spoteditor.backend.common.exceptions.ErrorCode;
import com.spoteditor.backend.common.util.CookieUtils;
import com.spoteditor.backend.domain.user.service.UserTokenService;
import com.spoteditor.backend.security.jwt.JwtConstants;
import com.spoteditor.backend.security.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserTokenService userTokenService;

    @PostMapping("/refresh")
    public ResponseEntity<List> refreshAccessToken (HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            userTokenService.refreshAccessToken(request, response);

            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        } catch (Exception e) {
            throw new BaseException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }
    }
}
