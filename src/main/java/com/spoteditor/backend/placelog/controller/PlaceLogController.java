package com.spoteditor.backend.placelog.controller;

import com.spoteditor.backend.placelog.controller.dto.PlaceLogRegisterRequest;
import com.spoteditor.backend.placelog.controller.dto.PlaceLogRegisterResponse;
import com.spoteditor.backend.placelog.service.PlaceLogService;
import com.spoteditor.backend.placelog.service.dto.PlaceLogRegisterCommand;
import com.spoteditor.backend.placelog.service.dto.PlaceLogRegisterResult;
import com.spoteditor.backend.user.common.dto.UserIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PlaceLogController {

    private final PlaceLogService placeLogService;

    @PostMapping("/placelogs")
    public ResponseEntity<PlaceLogRegisterResponse> addPlaceLog(
            @AuthenticationPrincipal UserIdDto dto,
            @RequestBody PlaceLogRegisterRequest request
    ) {
        PlaceLogRegisterCommand command = request.from();
        PlaceLogRegisterResult result = placeLogService.addPlaceLog(dto.getId(), command);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(PlaceLogRegisterResponse.from(result));
    }
}