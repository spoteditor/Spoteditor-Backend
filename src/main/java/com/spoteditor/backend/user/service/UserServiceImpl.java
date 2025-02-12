package com.spoteditor.backend.user.service;

import com.spoteditor.backend.global.exception.UserException;
import com.spoteditor.backend.place.entity.Place;
import com.spoteditor.backend.place.repository.PlaceRepository;
import com.spoteditor.backend.placelog.entity.PlaceLog;
import com.spoteditor.backend.user.entity.User;
import com.spoteditor.backend.user.repository.UserRepository;
import com.spoteditor.backend.user.service.dto.UserResult;
import com.spoteditor.backend.user.service.dto.UserUpdateCommand;
import com.spoteditor.backend.user.service.dto.UserUpdateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.spoteditor.backend.global.response.ErrorCode.NOT_FOUND_USER;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private PlaceRepository placeRepository;

    @Override
    public UserResult getUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(NOT_FOUND_USER));

        Long follower = 0L;
        Long following = 0L;
        List<PlaceLog> placeLogs = List.of();
        List<PlaceLog> bookmarkPlaceLogs = List.of();
        List<Place> bookmarkPlaces = List.of();

        return new UserResult(user, follower, following, placeLogs, bookmarkPlaceLogs, bookmarkPlaces);
    }

    @Override
    public UserUpdateResult updateUser(Long userId, UserUpdateCommand command) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(NOT_FOUND_USER));



        return UserUpdateResult(user);
    }


}
