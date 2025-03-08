package com.spoteditor.backend.placelog.event;

import com.spoteditor.backend.placelog.event.dto.PlaceLogPlaceImage;

import java.util.List;

public record PlaceLogRollbackEvent (
        List<PlaceLogPlaceImage> rollbackFiles
) {
}
