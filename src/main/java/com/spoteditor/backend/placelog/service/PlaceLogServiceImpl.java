package com.spoteditor.backend.placelog.service;

import com.spoteditor.backend.global.exception.PlaceLogException;
import com.spoteditor.backend.global.exception.UserException;
import com.spoteditor.backend.place.entity.Place;
import com.spoteditor.backend.place.repository.PlaceRepository;
import com.spoteditor.backend.placelog.controller.dto.PlaceLogRegisterRequest;
import com.spoteditor.backend.placelog.entity.PlaceLog;
import com.spoteditor.backend.placelog.repository.PlaceLogRepository;
import com.spoteditor.backend.placelog.service.dto.PlaceLogRegisterCommand;
import com.spoteditor.backend.placelog.service.dto.PlaceLogRegisterResult;
import com.spoteditor.backend.user.entity.User;
import com.spoteditor.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.spoteditor.backend.global.response.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class PlaceLogServiceImpl implements PlaceLogService {

    private final PlaceLogRepository placeLogRepository;
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;

    @Override
    @Transactional
    public PlaceLogRegisterResult addPlaceLog(Long userId, PlaceLogRegisterCommand command) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(NOT_FOUND_USER));

        if(command.placeIds().isEmpty()) {
            throw new PlaceLogException(PLACE_MINIMUM_REQUIRED);
        }

        if(command.placeIds().size() > 10) {
            throw new PlaceLogException(PLACE_LIMIT_EXCEEDED);
        }

        List<Place> places = placeRepository.findAllById(command.placeIds());

        if (places.size() != command.placeIds().size()) {
            throw new PlaceLogException(NOT_FOUND_PLACES);
        }

        PlaceLog savedPlaceLog = placeLogRepository.save(command.toEntity(user, places));

        return PlaceLogRegisterResult.from(savedPlaceLog, places);
    }
}
