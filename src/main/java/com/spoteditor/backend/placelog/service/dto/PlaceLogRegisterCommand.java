package com.spoteditor.backend.placelog.service.dto;

import com.spoteditor.backend.mapping.placelogplacemapping.entity.PlaceLogPlaceMapping;
import com.spoteditor.backend.mapping.placelogtagmapping.entity.PlaceLogTagMapping;
import com.spoteditor.backend.place.controller.dto.PlaceRegisterRequest;
import com.spoteditor.backend.place.entity.Place;
import com.spoteditor.backend.placelog.controller.dto.PlaceLogRegisterRequest;
import com.spoteditor.backend.placelog.entity.PlaceLog;
import com.spoteditor.backend.placelog.entity.PlaceLogStatus;
import com.spoteditor.backend.tag.dto.TagDto;
import com.spoteditor.backend.tag.entity.Tag;
import com.spoteditor.backend.user.entity.User;

import java.util.List;

public record PlaceLogRegisterCommand (
        String name,
        String description,
        PlaceLogStatus status,
        List<TagDto> tags,
        List<PlaceRegisterRequest> placeRegisterRequests
) {
    public static PlaceLogRegisterCommand from(PlaceLogRegisterRequest request) {
        return new PlaceLogRegisterCommand(
                request.name(),
                request.description(),
                request.status(),
                request.tags(),
                request.placeRegisterRequests()
        );
    }

    public PlaceLog toEntity(User user, List<Place> places, List<Tag> tags, PlaceLogStatus status) {

        Place firstPlace = places.get(0);

        PlaceLog placeLog = PlaceLog.builder()
                .user(user)
                .name(name)
                .description(description)
                .imageUrl(firstPlace.getPlaceImages().get(0).getStoredFile())
                .address(firstPlace.getAddress())
                .status(status)
                .build();

        // 로그 - 장소 중간테이블
        List<PlaceLogPlaceMapping> placeLogPlaceMappings = places.stream()
                .map(place -> PlaceLogPlaceMapping.builder()
                        .placeLog(placeLog)
                        .place(place)
                        .build())
                .toList();

        placeLog.getPlaceLogPlaceMappings().addAll(placeLogPlaceMappings);

        // 로그 - 태그 중간테이블
        List<PlaceLogTagMapping> placeLogTagMappings = tags.stream()
                .map(tag -> PlaceLogTagMapping.builder()
                        .placeLog(placeLog)
                        .tag(tag)
                        .build())
                .toList();

        placeLog.getPlaceLogTagMappings().addAll(placeLogTagMappings);

        return placeLog;
    }
}
