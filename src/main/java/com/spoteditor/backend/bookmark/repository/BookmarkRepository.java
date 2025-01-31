package com.spoteditor.backend.bookmark.repository;

import com.spoteditor.backend.bookmark.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

	Optional<Bookmark> findByUserIdAndPlaceId(Long userId, Long placeId);
}
