package com.spoteditor.backend.mapping.userplacelogmapping.entity;

import com.spoteditor.backend.global.common.BaseEntity;
import com.spoteditor.backend.placelog.entity.PlaceLog;
import com.spoteditor.backend.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_place_log_mapping")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPlaceLogMapping extends BaseEntity {

    @EmbeddedId
    private UserPlaceLogMappingId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @MapsId("placeLogId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_log_id")
    private PlaceLog placeLog;

    @Builder
    public UserPlaceLogMapping(User user, PlaceLog placeLog) {
        this.id = new UserPlaceLogMappingId(user.getId(), placeLog.getId());
        this.user = user;
        this.placeLog = placeLog;
    }
}
