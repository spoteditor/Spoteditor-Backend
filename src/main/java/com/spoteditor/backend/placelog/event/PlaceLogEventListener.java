package com.spoteditor.backend.placelog.service;

import com.spoteditor.backend.image.service.PlaceImageService;
import com.spoteditor.backend.placelog.service.dto.PlaceLogPlaceImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class PlaceLogEventListener {

    private final PlaceImageService placeImageService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void handleRollbackEvent(PlaceLogRollbackEvent event) {
        for(PlaceLogPlaceImage rollbackFile : event.rollbackFiles()) {
            placeImageService.rollbackUpload(rollbackFile);
        }
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleAfterCommitEvent(PlaceLogAfterCommitEvent event) {
        for(PlaceLogPlaceImage deleteFile : event.deleteFiles()) {
            placeImageService.deleteMainBucketImage(deleteFile);
        }
    }
}
