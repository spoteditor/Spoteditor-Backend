package com.spoteditor.backend.placelog.controller.dto;

import com.spoteditor.backend.tag.entity.TagCategory;
import com.spoteditor.backend.placelog.service.dto.TempPlaceLogRegisterCommand;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record TempPlaceLogRegisterRequest(
    String name,
    String category
) {
    public static TempPlaceLogRegisterCommand from(List<TempPlaceLogRegisterRequest> requests) {
        return new TempPlaceLogRegisterCommand(requests);
    }
}
