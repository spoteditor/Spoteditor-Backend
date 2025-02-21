package com.spoteditor.backend.placelog.service;

import com.spoteditor.backend.placelog.service.dto.*;
import org.springframework.stereotype.Service;

@Service
public interface PlaceLogService {

    PlaceLogResult addPlaceLog(Long userId, PlaceLogRegisterCommand command);

    TempPlaceLogRegisterResult addTempPlaceLogTag(Long userId, TempPlaceLogRegisterCommand command);

    PlaceLogResult getTempPlaceLog(Long userId, Long placeLogId);

    PlaceLogResult getPlaceLog(Long userId, Long placeLogId);

    PlaceLogResult addTempPlaceLogPlace(Long userId, PlaceLogPlaceCommand command);

    void publishPlaceLog(Long userId, Long placeLogId);

    void removePlaceLog(Long userId, Long placeLogId);
}