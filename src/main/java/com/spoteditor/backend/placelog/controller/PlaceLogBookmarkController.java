package com.spoteditor.backend.placelog.controller;

import com.spoteditor.backend.placelog.service.PlaceLogService;
import com.spoteditor.backend.user.common.dto.UserIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PlaceLogBookmarkController {

    private final PlaceLogService placeLogService;

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
