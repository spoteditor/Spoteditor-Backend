package com.spoteditor.backend.placelog.controller.dto;

import com.spoteditor.backend.image.controller.dto.PlaceImageResponse;
import com.spoteditor.backend.image.entity.PlaceImage;
import com.spoteditor.backend.place.controller.dto.PlaceResponse;
import com.spoteditor.backend.place.entity.Address;
import com.spoteditor.backend.placelog.entity.PlaceLogStatus;
import com.spoteditor.backend.placelog.service.dto.PlaceLogResult;
import com.spoteditor.backend.tag.dto.TagDto;

import java.util.List;

public record PlaceLogResponse(
        Long placeLogId,
        String name,
        String description,
        PlaceImageResponse image,
        Address address,
        PlaceLogStatus status,
        long views,
        List<TagDto> tags,
        List<PlaceLogPlaceRegisterResponse> places
) {
    public static PlaceLogResponse from(PlaceLogResult result) {
        return new PlaceLogResponse(
                result.placeLog().getId(),
                result.placeLog().getName(),
                result.placeLog().getDescription(),
                PlaceImageResponse.from(result.placeLog().getPlaceLogImage()),
                result.placeLog().getAddress(),
                result.placeLog().getStatus(),
                result.placeLog().getViews(),
                result.tags().stream()
                        .map(TagDto::from)
                        .toList(),
                result.places().stream()
                        .map(PlaceLogPlaceRegisterResponse::from)
                        .toList()
        );
    }
}
