package com.spoteditor.backend.user.service.dto;

public record UserUpdateCommand (
        String email,
        String name,
        String imageUrl,
        String description,
        String instagramId
) {
}
