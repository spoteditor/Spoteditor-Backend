package com.spoteditor.backend.placelog.repository;

import com.spoteditor.backend.config.page.CustomPageRequest;
import com.spoteditor.backend.config.page.CustomPageResponse;

import com.spoteditor.backend.placelog.controller.dto.PlaceLogListResponse;

public interface PlaceLogRepositoryCustom {

    CustomPageResponse<PlaceLogListResponse> findAllPlace(CustomPageRequest pageRequest);

    CustomPageResponse<PlaceLogListResponse> findMyPlaceLog(Long userId, CustomPageRequest pageRequest);

    CustomPageResponse<PlaceLogListResponse> findOtherPlaceLog(Long userId, CustomPageRequest pageRequest);

    CustomPageResponse<PlaceLogListResponse> findMyBookmarkPlaceLog(Long userId, CustomPageRequest pageRequest);

    CustomPageResponse<PlaceLogListResponse> searchBySidoBname(CustomPageRequest pageRequest, String sido, String bname);

    CustomPageResponse<PlaceLogListResponse> searchByName(CustomPageRequest pageRequest, String name);
}
