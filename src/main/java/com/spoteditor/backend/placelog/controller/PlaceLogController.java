package com.spoteditor.backend.placelog.controller;

import com.spoteditor.backend.config.page.CustomPageRequest;
import com.spoteditor.backend.config.page.CustomPageResponse;
import com.spoteditor.backend.config.swagger.PlaceLogApiDocument;
import com.spoteditor.backend.placelog.controller.dto.PlaceLogPlaceRequest;
import com.spoteditor.backend.placelog.controller.dto.PlaceLogResponse;
import com.spoteditor.backend.placelog.repository.PlaceLogRepository;
import com.spoteditor.backend.placelog.service.PlaceLogService;
import com.spoteditor.backend.placelog.service.dto.PlaceLogPlaceCommand;
import com.spoteditor.backend.placelog.service.dto.PlaceLogResult;
import com.spoteditor.backend.user.common.dto.UserIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PlaceLogController implements PlaceLogApiDocument {

    private final PlaceLogService placeLogService;
    private final PlaceLogRepository placeLogRepository;

    @Override
    @PostMapping("/placelogs/{placeLogId}")
    public ResponseEntity<Void> publishPlaceLog(
            @AuthenticationPrincipal UserIdDto userIdDto,
            @PathVariable Long placeLogId
    ) {
        placeLogService.publishPlaceLog(userIdDto.getId(), placeLogId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @Override
    @GetMapping("/placelogs")
    public ResponseEntity<CustomPageResponse<PlaceLogResponse>> getPlaceLogs(
            CustomPageRequest pageRequest
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(placeLogRepository.findAllPlace(pageRequest));
    }

    @PatchMapping("/placelogs/{placeLogId}")
    public ResponseEntity<PlaceLogResponse> updatePlaceLog(
            @AuthenticationPrincipal UserIdDto userIdDto,
            @PathVariable Long placeLogId,
            @RequestBody PlaceLogPlaceRequest request
    ) {
        PlaceLogPlaceCommand command = request.from(placeLogId);
        PlaceLogResult result = placeLogService.addTempPlaceLogPlace(userIdDto.getId(), command);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(PlaceLogResponse.from(result));
    }

    @GetMapping("/placelogs/{placeLogId}")
    public ResponseEntity<PlaceLogResponse> getPlaceLog(
            @AuthenticationPrincipal UserIdDto userIdDto,
            @PathVariable Long placeLogId
    ) {
        PlaceLogResult result = placeLogService.getPlaceLog(userIdDto.getId(), placeLogId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(PlaceLogResponse.from(result));
    }


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

    @PostMapping("/placelogs/{placeLogId}/bookmark")
    public ResponseEntity<Void> addBookmark(
            @AuthenticationPrincipal UserIdDto userIdDto,
            @PathVariable Long placeLogId
    ) {
        placeLogService.addBookmark(userIdDto.getId(), placeLogId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @DeleteMapping("/placelogs/{placeLogId}/bookmark")
    public ResponseEntity<Void> removeBookmark(
            @AuthenticationPrincipal UserIdDto userIdDto,
            @PathVariable Long placeLogId
    ) {
        placeLogService.removeBookmark(userIdDto.getId(), placeLogId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}