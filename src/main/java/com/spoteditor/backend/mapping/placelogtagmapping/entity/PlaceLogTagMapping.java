package com.spoteditor.backend.mapping.logplacetagmapping.entity;


import com.spoteditor.backend.placelog.entity.PlaceLog;
import com.spoteditor.backend.placelog.entity.Tag;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "log_place_tag_mapping")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LogPlaceTagMapping {

    @EmbeddedId
    private LogPlaceTagMappingId id;

    @MapsId("placeLogId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_log_id")
    private PlaceLog placeLog;

    @MapsId("tagId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Builder
    private LogPlaceTagMapping(PlaceLog placeLog, Tag tag) {
        this.id = new LogPlaceTagMappingId(placeLog.getId(), tag.getId());
        this.placeLog = placeLog;
        this.tag = tag;
    }
}
