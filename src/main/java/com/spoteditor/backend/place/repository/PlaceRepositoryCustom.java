package com.spoteditor.backend.place.repository;

import com.spoteditor.backend.config.page.CustomPageRequest;
import com.spoteditor.backend.config.page.CustomPageResponse;
import com.spoteditor.backend.place.controller.dto.PlaceResponse;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepositoryCustom {

	CustomPageResponse<PlaceResponse> findAllPlace(CustomPageRequest pageRequest);
}
