package com.spoteditor.backend.image.repository;

import com.spoteditor.backend.image.entity.PlaceImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<PlaceImage, Long> {
}
