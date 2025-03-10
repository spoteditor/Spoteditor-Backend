package com.spoteditor.backend.user.service;

import com.spoteditor.backend.user.entity.User;
import com.spoteditor.backend.user.service.dto.OtherUserResult;
import com.spoteditor.backend.user.service.dto.UserResult;
import com.spoteditor.backend.user.service.dto.UserUpdateCommand;
import com.spoteditor.backend.user.service.dto.UserUpdateResult;

public interface UserService {

    UserResult getUser(Long userId);

    OtherUserResult getOtherUser(Long userId, Long otherUserId);

    UserUpdateResult updateUser(Long userId, UserUpdateCommand command);

    void deleteUser(Long userId);
}
