package com.spoteditor.backend.user.controller;

import com.spoteditor.backend.config.page.CustomPageRequest;
import com.spoteditor.backend.config.page.CustomPageResponse;
import com.spoteditor.backend.place.controller.dto.PlaceResponse;
import com.spoteditor.backend.place.repository.PlaceRepository;
import com.spoteditor.backend.placelog.controller.dto.PlaceLogListResponse;
import com.spoteditor.backend.placelog.repository.PlaceLogRepository;
import com.spoteditor.backend.user.common.dto.UserIdDto;
import com.spoteditor.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserPlaceLogController {

    private final UserService userService;
    private final PlaceLogRepository placeLogRepository;
    private final PlaceRepository placeRepository;

    @GetMapping("/user/placelogs")
    public ResponseEntity<CustomPageResponse<PlaceLogListResponse>> getUserPlaceLogs(
            @AuthenticationPrincipal UserIdDto userIdDto,
            CustomPageRequest pageRequest
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(placeLogRepository.findMyPlaceLog(userIdDto.getId(), pageRequest));
    }

    @GetMapping("/user/bookmark/placelogs")
    public ResponseEntity<CustomPageResponse<PlaceLogListResponse>> getUserBookmarkPlaceLogs(
            @AuthenticationPrincipal UserIdDto userIdDto,
            CustomPageRequest pageRequest
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(placeLogRepository.findMyBookmarkPlaceLog(userIdDto.getId(), pageRequest));
    }

    @GetMapping("/user/bookmark/places")
    public ResponseEntity<CustomPageResponse<PlaceResponse>> getUserPlaces(
            @AuthenticationPrincipal UserIdDto userIdDto,
            CustomPageRequest pageRequest
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(placeRepository.findMyBookmarkPlace(userIdDto.getId(), pageRequest));
    }
}
