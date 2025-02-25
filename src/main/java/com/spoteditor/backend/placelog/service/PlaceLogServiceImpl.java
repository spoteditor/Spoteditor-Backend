package com.spoteditor.backend.placelog.service;

import com.spoteditor.backend.global.exception.*;
import com.spoteditor.backend.image.controller.dto.PlaceImageResponse;
import com.spoteditor.backend.image.entity.PlaceImage;
import com.spoteditor.backend.image.repository.PlaceImageRepository;
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
import com.spoteditor.backend.placelog.controller.dto.PlaceLogPlaceRegisterRequest;
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

import java.util.*;

import static com.spoteditor.backend.global.response.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlaceLogServiceImpl implements PlaceLogService {

    private final PlaceLogRepository placeLogRepository;
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final TagRepository tagRepository;
    private final PlaceLogTagMappingRepository placeLogTagMappingRepository;
    private final PlaceLogPlaceMappingRepository placeLogPlaceMappingRepository;
    private final PlaceService placeService;
    private final PlaceImageService imageService;
    private final PlaceImageRepository placeImageRepository;

    @Override
    @Transactional
    public PlaceLogResult addPlaceLog(Long userId, PlaceLogRegisterCommand command) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(NOT_FOUND_USER));

        if(user.isDeleted()) {
            throw new UserException(DELETED_USER);
        }

        // 로그에 제목 있어야함
        if(command.name() == null || command.name().trim().isEmpty()) {
            throw new PlaceLogException(NO_PLACE_LOG_NAME);
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
        List<PlaceLogPlaceRegisterRequest> placeRegisterRequests = command.placeRegisterRequests();

        Set<Place> placeSet = new LinkedHashSet<>();

        for(PlaceLogPlaceRegisterRequest request : placeRegisterRequests) {
            List<String> originalFiles = request.originalFiles();
            List<String> uuids = request.uuids();

            if (originalFiles.size() != uuids.size()) {
                throw new ImageException(IMAGE_UUID_MISMATCH);
            }

            if(originalFiles.isEmpty()) {
                throw new PlaceException(IMAGE_MINIMUM_REQUIRED);
            }

            if(originalFiles.size() > 3) {
                throw new PlaceException(IMAGE_LIMIT_EXCEEDED);
            }

            List<PlaceRegisterCommand> placeRegisterCommands = new ArrayList<>();

            for(int imageIndex = 0; imageIndex < originalFiles.size(); imageIndex++) {
                PlaceRegisterRequest placeRegisterRequest = PlaceRegisterRequest.builder()
                        .name(request.name())
                        .description(request.description())
                        .originalFile(originalFiles.get(imageIndex))
                        .uuid(uuids.get(imageIndex))
                        .address(request.address())
                        .category(request.category())
                        .build();

                placeRegisterCommands.add(placeRegisterRequest.from());
            }

            for(PlaceRegisterCommand placeRegisterCommand : placeRegisterCommands) {
                Place savedPlace = placeRepository.save(placeRegisterCommand.toEntity(user));
                imageService.upload(placeRegisterCommand.originalFile(), placeRegisterCommand.uuid(), savedPlace.getId());

                placeSet.add(savedPlace);
            }
        }
        List<Place> places = new ArrayList<>(placeSet);

        // 태그 등록
        List<String> tagNames = command.tags().stream()
                .map(TagDto::name)
                .toList();

        List<Tag> tags = tagRepository.findByNameIn(tagNames);

        // 로그 등록
        PlaceImageResponse placeLogImageResponse = imageService.uploadWithoutPlace(command.originalFile(), command.uuid());

        PlaceImage placeLogImage = placeImageRepository.findById(placeLogImageResponse.imageId())
                .orElseThrow(() -> new ImageException(NOT_FOUND_IMAGE));

        PlaceLog savedPlaceLog = placeLogRepository.save(command.toEntity(user, places, tags, placeLogImage));

        return PlaceLogResult.from(savedPlaceLog, places, tags);
    }

    @Override
    public PlaceLogResult getPlaceLog(Long userId, Long placeLogId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(NOT_FOUND_USER));

        PlaceLog placeLog = placeLogRepository.findById(placeLogId)
                .orElseThrow(() -> new PlaceLogException(NOT_FOUND_PLACE_LOG));

        // placeLog 주인 아닐 때 PlaceLog 가 private 이면 못봄
        if(!placeLog.getUser().getId().equals(userId) && placeLog.getStatus().equals(PlaceLogStatus.PRIVATE)) {
            throw new PlaceLogException(PRIVATE_PLACE_LOG);
        }

        return new PlaceLogResult(placeLog, getTags(placeLog.getId()), getPlaces(placeLog.getId()));
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

    private List<Place> getPlaces(Long placeLogId) {
        return placeLogPlaceMappingRepository.findByPlaceLogId(placeLogId).stream()
                .map(PlaceLogPlaceMapping::getPlace)
                .toList();
    }

    private List<Tag> getTags(Long placeLogId) {
        return placeLogTagMappingRepository.findByPlaceLogId(placeLogId).stream()
                .map(PlaceLogTagMapping::getTag)
                .toList();
    }
}
