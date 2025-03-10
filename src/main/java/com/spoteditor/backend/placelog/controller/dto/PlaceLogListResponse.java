package com.spoteditor.backend.placelog.controller.dto;

import com.spoteditor.backend.image.controller.dto.PlaceImageResponse;
import com.spoteditor.backend.image.entity.PlaceImage;
import com.spoteditor.backend.place.entity.Address;
import com.spoteditor.backend.placelog.entity.PlaceLogStatus;
import com.spoteditor.backend.tag.dto.TagDto;

import java.util.List;

public record PlaceLogListResponse (
        Long placeLogId,
        String author,
        String name,
        PlaceImageResponse image,
        Address address,
        long views
) {
}
