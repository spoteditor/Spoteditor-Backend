package com.spoteditor.backend.placelog.controller;

import com.spoteditor.backend.config.page.CustomPageRequest;
import com.spoteditor.backend.config.page.CustomPageResponse;
import com.spoteditor.backend.global.exception.PlaceLogException;
import com.spoteditor.backend.placelog.controller.dto.PlaceLogListResponse;
import com.spoteditor.backend.placelog.repository.PlaceLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.spoteditor.backend.global.response.ErrorCode.SEARCH_MINIMUM_REQUIRED_LETTERS;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PlaceLogSearchController {

    private final PlaceLogRepository placeLogRepository;

    @GetMapping("/search/placelogs/address")
    public ResponseEntity<CustomPageResponse<PlaceLogListResponse>> getPlaceLogsByAddress(
            CustomPageRequest pageRequest,
            String sido,
            String sigungu
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(placeLogRepository.searchBySidoSigungu(pageRequest, sido, sigungu));
    }

    @GetMapping("/search/placelogs/name")
    public ResponseEntity<CustomPageResponse<PlaceLogListResponse>> getPlaceLogsByName(
            CustomPageRequest pageRequest,
            String name
    ) {
        String searchName = name.trim();
        if(searchName.length() < 2) {
            throw new PlaceLogException(SEARCH_MINIMUM_REQUIRED_LETTERS);
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(placeLogRepository.searchByName(pageRequest, searchName));
    }
}
