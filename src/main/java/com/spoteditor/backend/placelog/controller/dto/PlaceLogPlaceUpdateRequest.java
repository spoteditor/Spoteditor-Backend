package com.spoteditor.backend.placelog.controller.dto;

import com.spoteditor.backend.place.entity.Address;
import com.spoteditor.backend.place.entity.Category;
import jakarta.annotation.Nullable;

import java.util.List;

public record PlaceLogPlaceUpdateRequest (
        Long id,
        @Nullable String description,
        @Nullable List<Long> deleteImageIds,
        @Nullable List<String> originalFiles,
        @Nullable List<String> uuids
) {
}