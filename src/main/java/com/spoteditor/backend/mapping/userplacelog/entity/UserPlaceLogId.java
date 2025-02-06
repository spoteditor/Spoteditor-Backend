package com.spoteditor.backend.mapping.userplacelog.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class UserPlaceLogId implements Serializable {
    private Long userId;
    private Long placeLogId;

    public UserPlaceLogId(Long userId, Long placeLogId) {
        this.userId = userId;
        this.placeLogId = placeLogId;
    }
}
