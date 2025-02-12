package com.spoteditor.backend.user.service;

import com.spoteditor.backend.user.service.dto.UserResult;
import com.spoteditor.backend.user.service.dto.UserUpdateCommand;
import com.spoteditor.backend.user.service.dto.UserUpdateResult;

public interface UserService {

    UserResult getUser(Long userId);

    UserUpdateResult updateUser(Long userId, UserUpdateCommand command);
}
