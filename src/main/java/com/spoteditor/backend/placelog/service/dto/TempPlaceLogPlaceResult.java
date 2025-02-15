package com.spoteditor.backend.placelog.service.dto;

import com.spoteditor.backend.place.entity.Place;
import com.spoteditor.backend.placelog.entity.PlaceLog;

import java.util.List;

public record TempPlaceLogPlaceResult (
    PlaceLog placeLog,
    List<Place> places
) {
}
