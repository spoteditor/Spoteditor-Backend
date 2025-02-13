package com.spoteditor.backend.follow.service;

import com.spoteditor.backend.follow.controller.dto.FollowRequest;
import com.spoteditor.backend.follow.entity.Follow;
import com.spoteditor.backend.follow.repository.FollowRepository;
import com.spoteditor.backend.global.exception.FollowException;
import com.spoteditor.backend.global.exception.UserException;
import com.spoteditor.backend.notification.dto.NotificationDto;
import com.spoteditor.backend.notification.service.NotificationService;
import com.spoteditor.backend.user.entity.User;
import com.spoteditor.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.spoteditor.backend.global.response.ErrorCode.*;
import static com.spoteditor.backend.notification.entity.NotificationType.FOLLOW;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowServiceImpl implements FollowService {

	private final FollowRepository followRepository;
	private final UserRepository userRepository;
	private final NotificationService notificationService;

	/**
	 *
	 * @param userId
	 * @param request
	 */
	@Transactional
	public void saveFollow(Long userId, FollowRequest request) {

		User follower = userRepository.findById(userId)
				.orElseThrow(() -> new UserException(NOT_FOUND_USER));

		User following = userRepository.findUserByEmail(request.email())
				.orElseThrow(() -> new UserException(NOT_FOUND_USER));

		followRepository.findFollowByFollowerAndFollowing(follower, following)
				.ifPresent(follow -> { throw new FollowException(DUPLICATED_FOLLOW); });

		Follow follow = Follow.builder()
				.follower(follower)
				.following(following)
				.build();

		NotificationDto dto = NotificationDto.builder()
				.type(FOLLOW)
				.message("[#] 알림: " + follower.getName() + "님이 " + following.getName() + "님을 팔로우했습니다.")
				.fromUser(follower)
				.toUser(following)
				.build();

		notificationService.send(dto);
		followRepository.save(follow);
	}

	/**
	 *
	 * @param userId
	 * @param request
	 */
	@Transactional
	public void removeFollow(Long userId, FollowRequest request) {

		User follower = userRepository.findById(userId)
				.orElseThrow(() -> new UserException(NOT_FOUND_USER));

		User following = userRepository.findUserByEmail(request.email())
				.orElseThrow(() -> new UserException(NOT_FOUND_USER));

		followRepository.deleteByFollowerAndFollowing(follower, following);
	}
}
