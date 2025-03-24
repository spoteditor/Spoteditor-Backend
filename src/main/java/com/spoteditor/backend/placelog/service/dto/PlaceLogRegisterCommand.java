package com.spoteditor.backend.placelog.service.dto;

import com.spoteditor.backend.image.entity.PlaceImage;
import com.spoteditor.backend.mapping.placelogplacemapping.entity.PlaceLogPlaceMapping;
import com.spoteditor.backend.mapping.placelogtagmapping.entity.PlaceLogTagMapping;
import com.spoteditor.backend.place.entity.Place;
import com.spoteditor.backend.placelog.controller.dto.PlaceLogPlaceRegisterRequest;
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
        String originalFile,
        String uuid,
        PlaceLogStatus status,
        List<TagDto> tags,
        List<PlaceLogPlaceRegisterRequest> placeRegisterRequests
) {
    public static PlaceLogRegisterCommand from(PlaceLogRegisterRequest request) {
        return new PlaceLogRegisterCommand(
                request.name(),
                request.description(),
                request.originalFile(),
                request.uuid(),
                request.status(),
                request.tags(),
                request.places()
        );
    }
}
