package com.spoteditor.backend.user.service;

import com.spoteditor.backend.follow.repository.FollowRepository;
import com.spoteditor.backend.global.exception.UserException;
import com.spoteditor.backend.place.entity.Place;
import com.spoteditor.backend.place.repository.PlaceRepository;
import com.spoteditor.backend.placelog.entity.PlaceLog;
import com.spoteditor.backend.placelog.repository.PlaceLogRepository;
import com.spoteditor.backend.user.entity.User;
import com.spoteditor.backend.user.repository.UserRepository;
import com.spoteditor.backend.user.service.dto.OtherUserResult;
import com.spoteditor.backend.user.service.dto.UserResult;
import com.spoteditor.backend.user.service.dto.UserUpdateCommand;
import com.spoteditor.backend.user.service.dto.UserUpdateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.spoteditor.backend.global.response.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    @Override
    @Transactional
    public UserResult getUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(NOT_FOUND_USER));

        if(user.isDeleted()) {
            throw new UserException(DELETED_USER);
        }

        Long follower = followRepository.countFollower(userId);
        Long following = followRepository.countFollowing(userId);

        return new UserResult(user, follower, following);
    }

    @Override
    @Transactional
    public OtherUserResult getOtherUser(Long userId, Long otherUserId) {

        UserResult userResult = getUser(otherUserId);
        boolean isFollowing;

        if(userId == null) {
            isFollowing = false;
        }
        else {
            if(userId.equals(otherUserId)) {
                throw new UserException(NOT_OTHER_USER);
            }

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserException(NOT_FOUND_USER));

            if(user.isDeleted()) {
                throw new UserException(DELETED_USER);
            }

            isFollowing = followRepository.findIsFollowing(userId, otherUserId);
        }

        return OtherUserResult.from(userResult.user(), userResult.follower(), userResult.following(), isFollowing);
    }

    @Override
    @Transactional
    public UserUpdateResult updateUser(Long userId, UserUpdateCommand command) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(NOT_FOUND_USER));

        if(user.isDeleted()) {
            throw new UserException(DELETED_USER);
        }

        user.update(command);
        userRepository.save(user);

        return new UserUpdateResult(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(NOT_FOUND_USER));

        if(user.isDeleted()) {
            throw new UserException(DELETED_USER);
        }

        user.softDelete();
        userRepository.save(user);
    }
}
