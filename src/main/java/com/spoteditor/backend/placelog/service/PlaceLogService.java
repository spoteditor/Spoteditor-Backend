package com.spoteditor.backend.placelog.service;

import com.spoteditor.backend.placelog.controller.dto.PlaceLogRegisterRequest;
import com.spoteditor.backend.placelog.service.dto.PlaceLogRegisterCommand;
import com.spoteditor.backend.placelog.service.dto.PlaceLogRegisterResult;
import org.springframework.stereotype.Service;

@Service
public interface PlaceLogService {

    PlaceLogRegisterResult addPlaceLog(Long userId, PlaceLogRegisterCommand command);
}