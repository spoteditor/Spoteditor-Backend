package com.spoteditor.backend.place.repository;

import com.spoteditor.backend.place.entity.Place;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Long>, PlaceRepositoryCustom {

	@Lock(LockModeType.OPTIMISTIC)
	@Query("SELECT p FROM Place p WHERE p.id = :id")
	Optional<Place> findByIdWithOptimisticLock(Long id);
}
