package com.spoteditor.backend.placelog.controller.dto;

import com.spoteditor.backend.place.entity.Address;
import com.spoteditor.backend.placelog.entity.PlaceLogStatus;
import com.spoteditor.backend.tag.dto.TagDto;

import java.util.List;

public record PlaceLogListResponse (
        Long placeLogId,
        String name,
        String description,
        String image,
        Address address,
        PlaceLogStatus status,
        long views,
        List<TagDto> tags
) {
}
