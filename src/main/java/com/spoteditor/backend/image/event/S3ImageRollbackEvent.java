package com.spoteditor.backend.image.event;

import com.spoteditor.backend.image.event.dto.S3Image;

import java.util.List;

public record S3ImageRollbackEvent(
        List<S3Image> rollbackFiles
) {
}
