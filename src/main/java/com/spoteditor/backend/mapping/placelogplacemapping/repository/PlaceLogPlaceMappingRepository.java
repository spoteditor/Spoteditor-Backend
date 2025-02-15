package com.spoteditor.backend.mapping.placelogplacemapping.repository;

import com.spoteditor.backend.mapping.placelogplacemapping.entity.PlaceLogPlaceMapping;
import com.spoteditor.backend.mapping.placelogplacemapping.entity.PlaceLogPlaceMappingId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceLogPlaceMappingRepository extends JpaRepository<PlaceLogPlaceMapping, PlaceLogPlaceMappingId>, PlaceLogPlaceMappingRepositoryCustom {


}
