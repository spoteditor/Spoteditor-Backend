package com.spoteditor.backend.placelog.entity;

import com.spoteditor.backend.global.common.BaseEntity;
import com.spoteditor.backend.place.entity.Address;
import com.spoteditor.backend.mapping.placelogplace.entity.PlaceLogPlace;
import com.spoteditor.backend.user.entity.User;

import com.spoteditor.backend.mapping.userplacelog.entity.UserPlaceLog;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "place_log")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaceLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_log_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "placeLog", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<UserPlaceLog> userPlaceLogs = new ArrayList<>();

    @OneToMany(mappedBy = "placeLog", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<PlaceLogPlace> placeLogPlaces = new ArrayList<>();

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "address")
    @Embedded
    private Address address;

    @Column(name = "views")
    private Long views;

    @Version
    private Long version;
}
