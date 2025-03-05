package com.spoteditor.backend.placelog.service.dto;

import com.spoteditor.backend.placelog.controller.dto.PlaceLogPlaceRegisterRequest;
import com.spoteditor.backend.placelog.controller.dto.PlaceLogPlaceUpdateRequest;
import com.spoteditor.backend.placelog.controller.dto.PlaceLogUpdateRequest;
import com.spoteditor.backend.placelog.entity.PlaceLogStatus;
import com.spoteditor.backend.tag.dto.TagDto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public record PlaceLogUpdateCommand (
    Optional<String> name,
    Optional<String> description,
    Optional<String> originalFile,
    Optional<String> uuid,
    Optional<PlaceLogStatus> status,
    List<TagDto> deleteTags,
    List<TagDto> addTags,
    List<Long> deletePlaceIds,
    List<PlaceLogPlaceRegisterRequest> addPlaces,
    List<PlaceLogPlaceUpdateCommand> updatePlaces
) {
    public static PlaceLogUpdateCommand from(PlaceLogUpdateRequest request) {
        return new PlaceLogUpdateCommand(
                Optional.ofNullable(request.name()),
                Optional.ofNullable(request.description()),
                Optional.ofNullable(request.originalFile()),
                Optional.ofNullable(request.uuid()),
                Optional.ofNullable(request.status()),
                request.deleteTags() != null ? request.deleteTags() : Collections.emptyList(),
                request.addTags() != null ? request.addTags() : Collections.emptyList(),
                request.deletePlaceIds() != null ? request.deletePlaceIds() : Collections.emptyList(),
                request.addPlaces() != null ? request.addPlaces() : Collections.emptyList(),
                request.updatePlaces() != null ? request.updatePlaces().stream()
                        .map(PlaceLogPlaceUpdateCommand::from)
                        .toList() : Collections.emptyList()
        );
    }
}
