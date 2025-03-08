package com.spoteditor.backend.follow.service;

import com.spoteditor.backend.follow.controller.dto.FollowRequest;

public interface FollowService {

	void saveFollow(Long userId, FollowRequest request);
	void removeFollow(Long userId, FollowRequest request);
}
