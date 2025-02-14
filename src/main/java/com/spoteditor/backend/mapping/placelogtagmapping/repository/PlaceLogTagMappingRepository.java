package com.spoteditor.backend.mapping.placelogtagmapping.repository;

import com.spoteditor.backend.mapping.placelogtagmapping.entity.PlaceLogTagMapping;
import com.spoteditor.backend.mapping.placelogtagmapping.entity.PlaceLogTagMappingId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceLogTagMappingRepository extends JpaRepository<PlaceLogTagMapping, PlaceLogTagMappingId>, PlaceLogTagMappingRepositoryCustom {

}
