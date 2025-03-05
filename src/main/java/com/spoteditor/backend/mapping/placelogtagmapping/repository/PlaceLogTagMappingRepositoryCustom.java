package com.spoteditor.backend.mapping.placelogtagmapping.repository;


import com.spoteditor.backend.mapping.placelogtagmapping.entity.PlaceLogTagMapping;
import com.spoteditor.backend.tag.entity.Tag;

import java.util.List;

public interface PlaceLogTagMappingRepositoryCustom {

    List<PlaceLogTagMapping> findByPlaceLogId(Long placeLogId);

    List<PlaceLogTagMapping> findByPlaceLogAndTagIn(Long placeLogId, List<Long> tagIds);
}
