package com.spoteditor.backend.mapping.logplacetagmapping.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class LogPlaceTagMappingId implements Serializable {
    private Long placeLogId;
    private Long tagId;

    public LogPlaceTagMappingId(Long placeLogId, Long tagId) {
        this.placeLogId = placeLogId;
        this.tagId = tagId;
    }
}
