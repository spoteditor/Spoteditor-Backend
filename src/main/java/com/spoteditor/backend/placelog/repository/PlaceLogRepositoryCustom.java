package com.spoteditor.backend.placelog.repository;

import com.spoteditor.backend.config.page.CustomPageRequest;
import com.spoteditor.backend.config.page.CustomPageResponse;

import com.spoteditor.backend.placelog.controller.dto.PlaceLogListResponse;
import com.spoteditor.backend.placelog.controller.dto.PlaceLogResponse;
import com.spoteditor.backend.placelog.entity.PlaceLog;

import java.util.List;
import java.util.Optional;

public interface PlaceLogRepositoryCustom {

    CustomPageResponse<PlaceLogListResponse> findAllPlace(CustomPageRequest pageRequest);

    CustomPageResponse<PlaceLogListResponse> findMyPlaceLog(Long userId, CustomPageRequest pageRequest);

    CustomPageResponse<PlaceLogListResponse> findMyBookmarkPlaceLog(Long userId, CustomPageRequest pageRequest);
}
