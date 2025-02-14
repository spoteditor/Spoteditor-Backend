package com.spoteditor.backend.placelog.service.dto;

import com.spoteditor.backend.place.entity.Place;
import com.spoteditor.backend.placelog.entity.PlaceLog;
import com.spoteditor.backend.tag.entity.Tag;

import java.util.List;

public record TempPlaceLogResult (
        PlaceLog placeLog,
        List<Tag> tags,
        List<Place> places
) {
}
