package com.spoteditor.backend.placelogplace.entity;

import com.spoteditor.backend.global.common.BaseEntity;
import com.spoteditor.backend.place.entity.Place;
import com.spoteditor.backend.placelog.entity.PlaceLog;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "place_log_place")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaceLogPlace extends BaseEntity {

    @EmbeddedId
    private PlaceLogPlaceId id;

    @MapsId("placeId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @MapsId("placeLogId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_log_id")
    private PlaceLog placeLog;
}
