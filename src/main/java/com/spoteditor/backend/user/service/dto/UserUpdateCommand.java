package com.spoteditor.backend.user.service.dto;

public record UserUpdateCommand (
        String name,
        String imageUrl,
        String description,
        String instagramId
) {
}
