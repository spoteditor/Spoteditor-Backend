package com.spoteditor.backend.user.service.dto;

import com.spoteditor.backend.user.entity.User;

public record OtherUserResult(
        Long userId,
        String name,
        String instagramId,
        String imageUrl,
        String description,
        Long follower,
        Long following,
        boolean isFollowing
) {
    public static OtherUserResult from(
            User user,
            Long follower,
            Long following,
            boolean isFollowing
    ){
        return new OtherUserResult(
                user.getId(),
                user.getName(),
                user.getInstagramId(),
                user.getImageUrl(),
                user.getDescription(),
                follower,
                following,
                isFollowing
        );
    }
}
