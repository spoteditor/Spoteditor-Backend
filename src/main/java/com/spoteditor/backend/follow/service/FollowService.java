package com.spoteditor.backend.follow.service;

import com.spoteditor.backend.follow.controller.dto.FollowRequest;
import com.spoteditor.backend.user.common.dto.UserIdDto;

public interface FollowService {

	void saveFollow(UserIdDto dto, FollowRequest request);
	void removeFollow(UserIdDto dto, FollowRequest request);
}
