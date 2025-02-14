package com.spoteditor.backend.placelog.controller.dto;

import com.spoteditor.backend.place.controller.dto.PlaceResponse;
import com.spoteditor.backend.place.entity.Address;
import com.spoteditor.backend.placelog.service.dto.PlaceLogResult;
import com.spoteditor.backend.tag.controller.dto.TagResponse;

import java.util.List;

public record PlaceLogResponse(
        Long placeLogId,
        String name,
        String description,
        String image,
        Address address,
        List<TagResponse> tags,
        List<PlaceResponse> places
) {
    public static PlaceLogResponse from(PlaceLogResult result) {
        return new PlaceLogResponse(
                result.placeLog().getId(),
                result.placeLog().getName(),
                result.placeLog().getDescription(),
                result.placeLog().getImageUrl(),
                result.placeLog().getAddress(),
                result.tags().stream()
                        .map(TagResponse::from)
                        .toList(),
                result.places().stream()
                        .map(PlaceResponse::from)
                        .toList()
        );
    }
}
