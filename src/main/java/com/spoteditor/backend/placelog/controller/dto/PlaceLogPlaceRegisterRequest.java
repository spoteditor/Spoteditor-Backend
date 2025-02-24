package com.spoteditor.backend.placelog.controller.dto;

import com.spoteditor.backend.place.entity.Address;
import com.spoteditor.backend.place.entity.Category;

import java.util.List;

public record PlaceLogPlaceRegisterRequest (
        String name,
        String description,
        Address address,
        Category category,
        List<String> originalFiles,
        List<String> uuids
) {
}