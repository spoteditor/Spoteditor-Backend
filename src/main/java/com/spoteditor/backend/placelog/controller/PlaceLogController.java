package com.spoteditor.backend.placelog.controller;

import com.spoteditor.backend.config.page.CustomPageRequest;
import com.spoteditor.backend.config.page.CustomPageResponse;
import com.spoteditor.backend.config.swagger.PlaceLogApiDocument;
import com.spoteditor.backend.global.exception.TokenException;
import com.spoteditor.backend.placelog.controller.dto.PlaceLogListResponse;
import com.spoteditor.backend.placelog.controller.dto.PlaceLogRegisterRequest;
import com.spoteditor.backend.placelog.controller.dto.PlaceLogResponse;
import com.spoteditor.backend.placelog.repository.PlaceLogRepository;
import com.spoteditor.backend.placelog.service.PlaceLogService;
import com.spoteditor.backend.placelog.service.dto.PlaceLogRegisterCommand;
import com.spoteditor.backend.placelog.service.dto.PlaceLogResult;
import com.spoteditor.backend.user.common.dto.UserIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PlaceLogController implements PlaceLogApiDocument {

    private final PlaceLogService placeLogService;
    private final PlaceLogRepository placeLogRepository;

    @Override
    @PostMapping("/placelogs")
    public ResponseEntity<PlaceLogResponse> savePlaceLog(
            @AuthenticationPrincipal UserIdDto userIdDto,
            @RequestBody PlaceLogRegisterRequest request
    ) {
        PlaceLogRegisterCommand command = PlaceLogRegisterCommand.from(request);
        PlaceLogResult result = placeLogService.addPlaceLog(userIdDto.getId(), command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(PlaceLogResponse.from(result));
    }

    @Override
    @GetMapping("/placelogs")
    public ResponseEntity<CustomPageResponse<PlaceLogListResponse>> getPlaceLogs(
            CustomPageRequest pageRequest
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(placeLogRepository.findAllPlace(pageRequest));
    }

    @Override
    @GetMapping("/placelogs/{placeLogId}")
    public ResponseEntity<PlaceLogResponse> getPlaceLog(
            @AuthenticationPrincipal UserIdDto userIdDto,
            @PathVariable Long placeLogId
    ) {
        PlaceLogResult result;

        if (userIdDto == null) {
            result = placeLogService.getPublicPlaceLog(placeLogId);
        } else {
            result = placeLogService.getPlaceLog(userIdDto.getId(), placeLogId);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(PlaceLogResponse.from(result));
    }

    @Override
    @DeleteMapping("/placelogs/{placeLogId}")
    public ResponseEntity<Void> removePlaceLog(
            @AuthenticationPrincipal UserIdDto userIdDto,
            @PathVariable Long placeLogId
    ) {
        placeLogService.removePlaceLog(userIdDto.getId(), placeLogId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}