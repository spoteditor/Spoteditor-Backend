package com.spoteditor.backend.mapping.placelogtagmapping.repository;


import com.spoteditor.backend.mapping.placelogtagmapping.entity.PlaceLogTagMapping;

import java.util.List;

public interface PlaceLogTagMappingRepositoryCustom {

    List<PlaceLogTagMapping> findByPlaceLogId(Long placeLogId);
}
