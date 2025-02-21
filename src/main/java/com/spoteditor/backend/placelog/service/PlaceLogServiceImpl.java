package com.spoteditor.backend.placelog.service;

import com.spoteditor.backend.global.exception.PlaceLogException;
import com.spoteditor.backend.global.exception.TagException;
import com.spoteditor.backend.global.exception.UserException;
import com.spoteditor.backend.image.service.PlaceImageService;
import com.spoteditor.backend.mapping.placelogplacemapping.entity.PlaceLogPlaceMapping;
import com.spoteditor.backend.mapping.placelogplacemapping.repository.PlaceLogPlaceMappingRepository;
import com.spoteditor.backend.mapping.placelogtagmapping.entity.PlaceLogTagMapping;
import com.spoteditor.backend.mapping.placelogtagmapping.repository.PlaceLogTagMappingRepository;
import com.spoteditor.backend.place.controller.dto.PlaceRegisterRequest;
import com.spoteditor.backend.place.entity.Place;
import com.spoteditor.backend.place.repository.PlaceRepository;
import com.spoteditor.backend.place.service.PlaceService;
import com.spoteditor.backend.place.service.dto.PlaceRegisterCommand;
import com.spoteditor.backend.place.service.dto.PlaceRegisterResult;
import com.spoteditor.backend.placelog.entity.PlaceLog;
import com.spoteditor.backend.placelog.entity.PlaceLogStatus;
import com.spoteditor.backend.placelog.service.dto.*;
import com.spoteditor.backend.tag.dto.TagDto;
import com.spoteditor.backend.tag.entity.Tag;
import com.spoteditor.backend.placelog.repository.PlaceLogRepository;
import com.spoteditor.backend.tag.repository.TagRepository;
import com.spoteditor.backend.user.entity.User;
import com.spoteditor.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.spoteditor.backend.global.response.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class PlaceLogServiceImpl implements PlaceLogService {

    private final PlaceLogRepository placeLogRepository;
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final TagRepository tagRepository;
    private final PlaceLogTagMappingRepository placeLogTagMappingRepository;
    private final PlaceLogPlaceMappingRepository placeLogPlaceMappingRepository;
    private final PlaceService placeService;
    private final PlaceImageService imageService;

    @Override
    @Transactional
    public PlaceLogResult addPlaceLog(Long userId, PlaceLogRegisterCommand command) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(NOT_FOUND_USER));

        if(user.isDeleted()) {
            throw new UserException(DELETED_USER);
        }

        // 장소는 1개 이상 등록
        if(command.placeRegisterRequests().isEmpty()) {
            throw new PlaceLogException(PLACE_MINIMUM_REQUIRED);
        }

        // 장소 최대 10개까지 등록
        if(command.placeRegisterRequests().size() > 10) {
            throw new PlaceLogException(PLACE_LIMIT_EXCEEDED);
        }

        // 장소 등록
        List<PlaceRegisterCommand> placeRegisterCommands = command.placeRegisterRequests().stream()
                .map(PlaceRegisterRequest::from)
                .toList();

        List<Place> places = new ArrayList<>();

        for(PlaceRegisterCommand placeRegisterCommand : placeRegisterCommands) {
            Place savedPlace = placeRepository.save(placeRegisterCommand.toEntity(user));
            imageService.upload(placeRegisterCommand.originalFile(), placeRegisterCommand.uuid(), savedPlace.getId());

            places.add(savedPlace);
        }

        // 태그 등록
        List<String> tagNames = command.tags().stream()
                .map(TagDto::name)
                .toList();

        List<Tag> tags = tagRepository.findByNameIn(tagNames);

        // 로그 등록
        PlaceLog savedPlaceLog = placeLogRepository.save(command.toEntity(user, places, tags, command.status()));

        return PlaceLogResult.from(savedPlaceLog, places, tags);
    }

    @Override
    @Transactional
    public TempPlaceLogRegisterResult addTempPlaceLogTag(Long userId, TempPlaceLogRegisterCommand command) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(NOT_FOUND_USER));

        Optional<PlaceLog> tempPlaceLog = placeLogRepository.findTempPlaceLogByUser(user.getId());

        if(tempPlaceLog.isPresent()) {
            throw new PlaceLogException(TEMP_PLACE_LOG_ALREADY_EXIST);
        }

        if(command.tags().size() > 5) {
            throw new TagException(TAG_LIMIT_EXCEEDED);
        }

        List<Tag> tags = tagRepository.findByNameIn(command.getTagNames());

        if(command.tags().size() != tags.size()) {
            throw new TagException(INVALID_TAG);
        }

        PlaceLog savedTempPlaceLog = placeLogRepository.save(command.toEntity(user, tags));

        return new TempPlaceLogRegisterResult(savedTempPlaceLog);
    }

    @Override
    @Transactional
    public PlaceLogResult getTempPlaceLog(Long userId, Long placeLogId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(NOT_FOUND_USER));

        PlaceLog tempPlaceLog = placeLogRepository.findById(placeLogId)
                .orElseThrow(() -> new PlaceLogException(NOT_FOUND_PLACE_LOG));

        if(!tempPlaceLog.getUser().getId().equals(userId)) {
            throw new PlaceLogException(NOT_PLACE_LOG_OWNER);
        }

        if(!tempPlaceLog.getStatus().equals(PlaceLogStatus.TEMP)) {
            throw new PlaceLogException(NOT_TEMP_PLACE_LOG);
        }

        List<Tag> tags = placeLogTagMappingRepository.findByPlaceLogId(tempPlaceLog.getId()).stream()
                .map(PlaceLogTagMapping::getTag)
                .toList();

        List<Place> places = placeLogPlaceMappingRepository.findByPlaceLogId(tempPlaceLog.getId()).stream()
                .map(PlaceLogPlaceMapping::getPlace)
                .toList();

        return new PlaceLogResult(tempPlaceLog, tags, places);
    }

    @Override
    @Transactional
    public PlaceLogResult getPlaceLog(Long userId, Long placeLogId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(NOT_FOUND_USER));

        PlaceLog tempPlaceLog = placeLogRepository.findById(placeLogId)
                .orElseThrow(() -> new PlaceLogException(NOT_FOUND_PLACE_LOG));

        if(!tempPlaceLog.getStatus().equals(PlaceLogStatus.PUBLISHED)) {
            throw new PlaceLogException(NOT_PUBLISHED_PLACE_LOG);
        }

        List<Tag> tags = placeLogTagMappingRepository.findByPlaceLogId(tempPlaceLog.getId()).stream()
                .map(PlaceLogTagMapping::getTag)
                .toList();

        List<Place> places = placeLogPlaceMappingRepository.findByPlaceLogId(tempPlaceLog.getId()).stream()
                .map(PlaceLogPlaceMapping::getPlace)
                .toList();

        return new PlaceLogResult(tempPlaceLog, tags, places);
    }

    @Override
    @Transactional
    public PlaceLogResult addTempPlaceLogPlace(Long userId, PlaceLogPlaceCommand command) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(NOT_FOUND_USER));

        PlaceLog tempPlaceLog = placeLogRepository.findById(command.placeLogId())
                .orElseThrow(() -> new PlaceLogException(NOT_FOUND_PLACE_LOG));

        if(!tempPlaceLog.getUser().getId().equals(userId)) {
            throw new PlaceLogException(NOT_PLACE_LOG_OWNER);
        }
        // 이름, 설명 업데이트
        tempPlaceLog.update(command.name(), command.description());

        // 태그 가져오기
        List<Tag> tags = placeLogTagMappingRepository.findByPlaceLogId(tempPlaceLog.getId()).stream()
                .map(PlaceLogTagMapping::getTag)
                .toList();

        List<PlaceRegisterCommand> placeRegisterCommands = command.places();

        List<PlaceRegisterResult> placeRegisterResults = new ArrayList<>();

        for(PlaceRegisterCommand placeRegisterCommand: placeRegisterCommands) {
            placeRegisterResults.add(placeService.addPlace(userId, placeRegisterCommand));
        }

        List<Long> placeIds = placeRegisterResults.stream()
                                .map(PlaceRegisterResult::placeId)
                                .toList();
        // 장소 가져오기
        List<Place> places = placeRepository.findByIdIn(placeIds);

        // 중간테이블 생성
        places.forEach(place -> {
            PlaceLogPlaceMapping mapping = PlaceLogPlaceMapping.builder()
                    .placeLog(tempPlaceLog)
                    .place(place)
                    .build();
            tempPlaceLog.getPlaceLogPlaceMappings().add(mapping);
        });

        placeLogRepository.save(tempPlaceLog);

        return new PlaceLogResult(tempPlaceLog, tags, places);
    }

    @Override
    @Transactional
    public void publishPlaceLog(Long userId, Long placeLogId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(NOT_FOUND_USER));

        PlaceLog tempPlaceLog = placeLogRepository.findById(placeLogId)
                .orElseThrow(() -> new PlaceLogException(NOT_FOUND_PLACE_LOG));

        if(!tempPlaceLog.getUser().getId().equals(userId)) {
            throw new PlaceLogException(NOT_PLACE_LOG_OWNER);
        }

        if(tempPlaceLog.getName().isEmpty()) {
            throw new PlaceLogException(NO_PLACE_LOG_NAME);
        }

        List<Long> placeIds = placeLogPlaceMappingRepository.findByPlaceLogId(placeLogId).stream()
                .map(placeLogPlaceMapping -> placeLogPlaceMapping.getPlace().getId())
                .toList();

        if(placeIds.isEmpty()) {
            throw new PlaceLogException(PLACE_MINIMUM_REQUIRED);
        }

        if(placeIds.size() > 10) {
            throw new PlaceLogException(PLACE_LIMIT_EXCEEDED);
        }
        tempPlaceLog.publish();

        placeLogRepository.save(tempPlaceLog);
    }

    @Override
    @Transactional
    public void removePlaceLog(Long userId, Long placeLogId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(NOT_FOUND_USER));

        PlaceLog placeLog = placeLogRepository.findById(placeLogId)
                .orElseThrow(() -> new PlaceLogException(NOT_FOUND_PLACE_LOG));

        if(!placeLog.getUser().getId().equals(userId)) {
            throw new PlaceLogException(NOT_PLACE_LOG_OWNER);
        }

        placeLogRepository.delete(placeLog);
    }
}
