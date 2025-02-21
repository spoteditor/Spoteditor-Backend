package com.spoteditor.backend.placelog.controller.dto;

import com.spoteditor.backend.place.controller.dto.PlaceRegisterRequest;
import com.spoteditor.backend.placelog.entity.PlaceLogStatus;
import com.spoteditor.backend.tag.dto.TagDto;

import java.util.List;

public record PlaceLogRegisterRequest (
        String name,
        String description,
        PlaceLogStatus status,
        List<TagDto> tags,
        List<PlaceRegisterRequest> placeRegisterRequests
) {
}
