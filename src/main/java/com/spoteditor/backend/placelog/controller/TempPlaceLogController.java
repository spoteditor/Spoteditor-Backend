package com.spoteditor.backend.placelog.controller;

import com.spoteditor.backend.config.swagger.TempPlaceLogApiDocument;
import com.spoteditor.backend.placelog.controller.dto.PlaceLogPlaceRequest;
import com.spoteditor.backend.placelog.controller.dto.PlaceLogResponse;
import com.spoteditor.backend.placelog.service.PlaceLogService;
import com.spoteditor.backend.placelog.controller.dto.TempPlaceLogRegisterResponse;
import com.spoteditor.backend.placelog.service.dto.*;
import com.spoteditor.backend.user.common.dto.UserIdDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TempPlaceLogController implements TempPlaceLogApiDocument {

    private final PlaceLogService placeLogService;

    @Override
    @PostMapping("/temp-placelogs")
    public ResponseEntity<TempPlaceLogRegisterResponse> saveTempPlaceLogTag (
            @AuthenticationPrincipal UserIdDto userIdDto,
            @RequestBody TempPlaceLogRegisterCommand command
    ) {
        TempPlaceLogRegisterResult result = placeLogService.addTempPlaceLogTag(
                userIdDto.getId(), command
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(TempPlaceLogRegisterResponse.from(result));
    }

    @GetMapping("/temp-placelogs/{placeLogId}")
    public ResponseEntity<PlaceLogResponse> getTempPlaceLog (
            @AuthenticationPrincipal UserIdDto userIdDto,
            @PathVariable Long placeLogId
    ) {
        PlaceLogResult result = placeLogService.getTempPlaceLog(userIdDto.getId(), placeLogId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(PlaceLogResponse.from(result));
    }

    @PostMapping("/temp-placelogs/{placeLogId}/places")
    public ResponseEntity<PlaceLogResponse> saveTempPlaceLogPlace (
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
}
