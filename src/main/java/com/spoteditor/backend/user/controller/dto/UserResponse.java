package com.spoteditor.backend.user.controller.dto;

import com.spoteditor.backend.user.service.dto.UserResult;

import java.util.List;

public record UserResponse(
        String name,
        String instagramId,
        String imageUrl,
        String description,
        Long follower,
        Long following
) {
    public static UserResponse from(UserResult userResult) {
        return new UserResponse(
                userResult.user().getName(),
                userResult.user().getInstagramId(),
                userResult.user().getImageUrl(),
                userResult.user().getDescription(),
                userResult.follower(),
                userResult.following()
        );
    }
}
