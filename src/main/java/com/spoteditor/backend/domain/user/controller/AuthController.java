package com.spoteditor.backend.domain.user.controller;

import com.spoteditor.backend.common.exceptions.user.UserException;
import com.spoteditor.backend.common.exceptions.user.UserErrorCode;
import com.spoteditor.backend.domain.user.service.UserTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Void> refreshAccessToken (HttpServletRequest request, HttpServletResponse response) throws Exception {
        userTokenService.refreshAccessToken(request, response);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
