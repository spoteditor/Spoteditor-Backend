package com.spoteditor.backend.place.repository;

import com.spoteditor.backend.config.page.CustomPageRequest;
import com.spoteditor.backend.config.page.CustomPageResponse;
import com.spoteditor.backend.place.controller.dto.PlaceResponse;
import com.spoteditor.backend.place.entity.Place;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepositoryCustom {

	CustomPageResponse<PlaceResponse> findAllPlace(CustomPageRequest pageRequest);

	List<Place> findByIdIn(List<Long> placeIds);

	List<Place> findAllPlacesByUserId(Long userId);

}
