package com.spoteditor.backend.placelog.service.dto;

import com.spoteditor.backend.mapping.placelogtagmapping.entity.PlaceLogTagMapping;
import com.spoteditor.backend.placelog.entity.PlaceLog;
import com.spoteditor.backend.placelog.entity.PlaceLogStatus;
import com.spoteditor.backend.tag.entity.Tag;
import com.spoteditor.backend.user.entity.User;

import java.util.List;

public record TempPlaceLogRegisterCommand (
        List<TempPlaceLogRegisterRequest> tags
){
    public List<String> getTagNames() {
        return tags.stream()
                .map(TempPlaceLogRegisterRequest::name)
                .toList();
    }

    public PlaceLog toEntity(User user, List<Tag> tags) {

        PlaceLog placeLog = PlaceLog.builder()
                .user(user)
                .status(PlaceLogStatus.TEMP)
                .build();

        tags.forEach(tag -> {
            PlaceLogTagMapping mapping = PlaceLogTagMapping.builder()
                    .placeLog(placeLog)
                    .tag(tag)
                    .build();
            placeLog.getPlaceLogTagMappings().add(mapping);
        });

        return placeLog;
    }
}
