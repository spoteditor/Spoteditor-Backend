package com.spoteditor.backend.user.controller.dto;

import com.spoteditor.backend.user.service.dto.UserResult;

import java.util.List;

public record UserResponse(
        String name,
        String instagramId,
        String imageUrl,
        String description,
        Long follower,
        Long following,
        List<UserPlaceLogDto> userPlaceLogDtoList,
        List<UserBookmarkPlaceLogDto> userBookmarkPlaceLogDtoList,
        List<UserBookmarkPlaceDto> userBookmarkPlaceDtoList
) {
    public static UserResponse from(UserResult userResult) {
        return new UserResponse(
                userResult.user().getName(),
                userResult.user().getInstagramId(),
                userResult.user().getImageUrl(),
                userResult.user().getDescription(),
                userResult.follower(),
                userResult.following(),
                userResult.placeLogs().stream()
                        .map(UserPlaceLogDto::from)
                        .toList(),
                userResult.bookmarkPlaceLogs().stream()
                        .map(UserBookmarkPlaceLogDto::from)
                        .toList(),
                userResult.bookmarkPlaces().stream()
                        .map(UserBookmarkPlaceDto::from)
                        .toList()
        );
    }
}
