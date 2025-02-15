package com.spoteditor.backend.mapping.placelogplacemapping.repository;

import com.spoteditor.backend.mapping.placelogplacemapping.entity.PlaceLogPlaceMapping;

import java.util.List;

public interface PlaceLogPlaceMappingRepositoryCustom {

    List<PlaceLogPlaceMapping> findByPlaceLogId(Long placeLogId);
}
