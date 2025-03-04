package com.spoteditor.backend.follow.service.facade;

import com.spoteditor.backend.bookmark.aop.DistributedLock;
import com.spoteditor.backend.follow.controller.dto.FollowRequest;
import com.spoteditor.backend.follow.service.FollowService;
import com.spoteditor.backend.user.common.dto.UserIdDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FollowFacade {

	private final FollowService followService;

	@DistributedLock(key = "#request.userId()")
	public void saveFollow(UserIdDto dto, FollowRequest request) {
		followService.saveFollow(dto, request);
	}

	public void removeFollow(UserIdDto dto, FollowRequest request) {
		followService.removeFollow(dto, request);
	}
}
