package com.spoteditor.backend.follow.controller.dto;

public record FollowResponse(
	Long UserId,
	String name,
	String email,
	String imageUrl
) {
}
