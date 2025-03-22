package com.spoteditor.backend.user.controller.dto;

import com.spoteditor.backend.user.service.dto.UserUpdateCommand;
import jakarta.annotation.Nullable;

public record UserUpdateRequest (
        @Nullable String name,
        @Nullable String originalFile,
        @Nullable String uuid,
        @Nullable String description,
        @Nullable String instagramId
) {
    public UserUpdateCommand from(){
        return new UserUpdateCommand(
                name,
                originalFile,
                uuid,
                description,
                instagramId
        );
    }
}
