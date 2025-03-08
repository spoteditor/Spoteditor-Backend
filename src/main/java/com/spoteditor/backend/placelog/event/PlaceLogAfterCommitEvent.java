package com.spoteditor.backend.placelog.service;

import com.spoteditor.backend.placelog.service.dto.PlaceLogPlaceImage;

import java.util.List;

public record PlaceLogAfterCommitEvent (
        List<PlaceLogPlaceImage> deleteFiles
) {
}