package com.spoteditor.backend.mapping.placelogplacemapping.entity;

import com.spoteditor.backend.global.common.BaseEntity;
import com.spoteditor.backend.place.entity.Place;
import com.spoteditor.backend.placelog.entity.PlaceLog;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "place_log_place_mapping")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaceLogPlaceMapping extends BaseEntity {

    @EmbeddedId
    private PlaceLogPlaceMappingId id;

    @MapsId("placeId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @MapsId("placeLogId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_log_id")
    private PlaceLog placeLog;

    @Builder
    private PlaceLogPlaceMapping(PlaceLog placeLog, Place place) {
        this.id = new PlaceLogPlaceMappingId(placeLog.getId(), place.getId());
        this.placeLog = placeLog;
        this.place = place;
    }
}
