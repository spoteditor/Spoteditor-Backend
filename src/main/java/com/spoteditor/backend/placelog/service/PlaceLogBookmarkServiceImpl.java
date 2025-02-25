package com.spoteditor.backend.placelog.service;

import com.spoteditor.backend.global.exception.BookmarkException;
import com.spoteditor.backend.global.exception.PlaceLogException;
import com.spoteditor.backend.global.exception.UserException;
import com.spoteditor.backend.mapping.placelogplacemapping.repository.PlaceLogPlaceMappingRepository;
import com.spoteditor.backend.mapping.placelogtagmapping.repository.PlaceLogTagMappingRepository;
import com.spoteditor.backend.mapping.userplacelogmapping.entity.UserPlaceLogMapping;
import com.spoteditor.backend.mapping.userplacelogmapping.entity.UserPlaceLogMappingId;
import com.spoteditor.backend.mapping.userplacelogmapping.repository.UserPlaceLogMappingRepository;
import com.spoteditor.backend.place.repository.PlaceRepository;
import com.spoteditor.backend.place.service.PlaceService;
import com.spoteditor.backend.placelog.entity.PlaceLog;
import com.spoteditor.backend.placelog.repository.PlaceLogRepository;
import com.spoteditor.backend.tag.repository.TagRepository;
import com.spoteditor.backend.user.entity.User;
import com.spoteditor.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.spoteditor.backend.global.response.ErrorCode.*;
import static com.spoteditor.backend.global.response.ErrorCode.BOOKMARK_ALREADY_REMOVED;

@Service
@RequiredArgsConstructor
public class PlaceLogBookmarkServiceImpl implements PlaceLogBookmarkService{

    private final PlaceLogRepository placeLogRepository;
    private final UserRepository userRepository;
    private final UserPlaceLogMappingRepository userPlaceLogMappingRepository;

    @Transactional
    public void addBookmark(Long userId, Long placeLogId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(NOT_FOUND_USER));

        PlaceLog placeLog = placeLogRepository.findById(placeLogId)
                .orElseThrow(() -> new PlaceLogException(NOT_FOUND_PLACE_LOG));

        UserPlaceLogMappingId mappingId = new UserPlaceLogMappingId(userId, placeLogId);

        if (userPlaceLogMappingRepository.existsById(mappingId)) {
            throw new BookmarkException(BOOKMARK_ALREADY_EXIST);
        }

        UserPlaceLogMapping mapping = new UserPlaceLogMapping(user, placeLog);
        userPlaceLogMappingRepository.save(mapping);
    }

    @Transactional
    public void removeBookmark(Long userId, Long placeLogId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(NOT_FOUND_USER));

        PlaceLog placeLog = placeLogRepository.findById(placeLogId)
                .orElseThrow(() -> new PlaceLogException(NOT_FOUND_PLACE_LOG));

        UserPlaceLogMappingId mappingId = new UserPlaceLogMappingId(userId, placeLogId);

        if (!userPlaceLogMappingRepository.existsById(mappingId)) {
            throw new BookmarkException(BOOKMARK_ALREADY_REMOVED);
        }

        UserPlaceLogMapping mapping = new UserPlaceLogMapping(user, placeLog);
        userPlaceLogMappingRepository.delete(mapping);
    }
}
