package com.spoteditor.backend.placelog.service;

import org.springframework.stereotype.Service;

@Service
public interface PlaceLogBookmarkService {

    void addBookmark(Long userId, Long placeLogId);

    void removeBookmark(Long userId, Long placeLogId);
}
