package com.spoteditor.backend.user.controller.dto;

import com.spoteditor.backend.user.service.dto.UserUpdateResult;

public record UserUpdateResponse (
        String email,
        String name,
        String imageUrl,
        String description,
        String instagramId
) {
    public static UserUpdateResponse from(UserUpdateResult result) {
        return new UserUpdateResponse(
                result.user().getEmail(),
                result.user().getName(),
                result.user().getImageUrl(),
                result.user().getDescription(),
                result.user().getInstagramId()
        );
    }
}
