package com.spoteditor.backend.follow.service;

import com.spoteditor.backend.follow.controller.dto.FollowRequest;
import com.spoteditor.backend.follow.entity.Follow;
import com.spoteditor.backend.follow.repository.FollowRepository;
import com.spoteditor.backend.global.exception.FollowException;
import com.spoteditor.backend.global.exception.UserException;
import com.spoteditor.backend.notification.dto.NotificationDto;
import com.spoteditor.backend.notification.service.NotificationService;
import com.spoteditor.backend.user.common.dto.UserIdDto;
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
	 * @param dto
	 * @param request
	 */
	@Transactional
	public void saveFollow(UserIdDto dto, FollowRequest request) {

		User follower = userRepository.findById(dto.getId())
				.orElseThrow(() -> new UserException(NOT_FOUND_USER));

		User following = userRepository.findById(request.userId())
				.orElseThrow(() -> new UserException(NOT_FOUND_USER));

		// 자기 자신 팔로우를 허용하지 않는다.
		validateNotSelfFollow(follower, following);

		// 중복된 팔로우인지 체크한다.
		validateDuplicatedFollow(follower, following);

		Follow follow = Follow.builder()
				.follower(follower)
				.following(following)
				.build();

		sendFollowNotification(follower, following);
		followRepository.save(follow);
	}

	/**
	 *
	 * @param dto
	 * @param request
	 */
	@Transactional
	public void removeFollow(UserIdDto dto, FollowRequest request) {

		User follower = userRepository.findById(dto.getId())
				.orElseThrow(() -> new UserException(NOT_FOUND_USER));

		User following = userRepository.findById(request.userId())
				.orElseThrow(() -> new UserException(NOT_FOUND_USER));

		validateNotSelfUnFollow(follower, following);
		followRepository.deleteByFollowerAndFollowing(follower, following);
	}

	private static void validateNotSelfFollow(User follower, User following) {
		if (follower.getId().equals(following.getId())) {
			throw new FollowException(SELF_FOLLOW_NOT_ALLOWED);
		}
	}

	private static void validateNotSelfUnFollow(User follower, User following) {
		if (follower.getId().equals(following.getId())) {
			throw new FollowException(SELF_FOLLOW_NOT_ALLOWED);
		}
	}

	private void validateDuplicatedFollow(User follower, User following) {
		followRepository.findFollowByFollowerAndFollowing(follower, following)
				.ifPresent(follow -> { throw new FollowException(DUPLICATED_FOLLOW); });
	}

	private void sendFollowNotification(User follower, User following) {
		NotificationDto notificationDto = NotificationDto.builder()
				.type(FOLLOW)
				.message("[#] 알림: " + follower.getName() + "님이 " + following.getName() + "님을 팔로우했습니다.")
				.fromUser(follower)
				.toUser(following)
				.build();

		notificationService.send(notificationDto);
	}
}
