package com.spoteditor.backend.bookmark.repository;

import com.spoteditor.backend.bookmark.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

	Optional<Bookmark> findByUserIdAndPlaceId(@Param("userId") Long userId, @Param("placeId") Long placeId);
}
