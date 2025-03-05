package com.spoteditor.backend.placelog.service;

import com.spoteditor.backend.placelog.service.dto.*;
import org.springframework.stereotype.Service;

@Service
public interface PlaceLogService {

    PlaceLogResult addPlaceLog(Long userId, PlaceLogRegisterCommand command);

    PlaceLogResult getPlaceLog(Long userId, Long placeLogId);

    PlaceLogResult updatePlaceLog(Long userId, Long placeLogId, PlaceLogUpdateCommand command);

    PlaceLogResult getPublicPlaceLog(Long placeLogId);

    void removePlaceLog(Long userId, Long placeLogId);
}