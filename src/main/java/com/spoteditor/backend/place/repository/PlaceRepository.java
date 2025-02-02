package com.spoteditor.backend.place.repository;

import com.spoteditor.backend.place.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long>, PlaceRepositoryCustom {

}
