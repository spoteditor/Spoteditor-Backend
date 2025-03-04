package com.spoteditor.backend.follow.controller.dto;

public record FollowResponse(
	Long userId,
	String name,
	String email,
	String imageUrl
) {
}
