package com.spoteditor.backend.user.controller;

import com.spoteditor.backend.user.common.dto.UserTokenDto;
import com.spoteditor.backend.user.controller.dto.UserResponse;
import com.spoteditor.backend.user.controller.dto.UserUpdateRequest;
import com.spoteditor.backend.user.service.UserService;
import com.spoteditor.backend.user.service.dto.UserResult;
import com.spoteditor.backend.user.service.dto.UserUpdateCommand;
import com.spoteditor.backend.user.service.dto.UserUpdateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<UserResponse> getUser(
            @AuthenticationPrincipal UserTokenDto userTokenDto
    ) {
        UserResult userResult = userService.getUser(userTokenDto.getId());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(UserResponse.from(userResult));
    }

    @PatchMapping("/users")
    public ResponseEntity<UserUpdateResponse> updateUser(
            @AuthenticationPrincipal UserTokenDto userTokenDto,
            @RequestBody UserUpdateRequest userUpdateRequest
    ) {
        UserUpdateCommand command = userUpdateRequest.from();
        UserUpdateResult result = userService.updateUser(userTokenDto.getId(), command);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(UserUpdateResponse.from(result));
    }
}
