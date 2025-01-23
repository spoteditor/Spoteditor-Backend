package com.spoteditor.backend.place.repository;

import com.spoteditor.backend.config.page.PageRequest;
import com.spoteditor.backend.config.page.PageResponse;
import com.spoteditor.backend.place.controller.dto.PlaceResponse;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepositoryCustom {

	PageResponse<PlaceResponse> findAllPlace(PageRequest pageRequest);
}
