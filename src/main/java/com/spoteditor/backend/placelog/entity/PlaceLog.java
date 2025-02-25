package com.spoteditor.backend.placelog.entity;

import com.spoteditor.backend.global.common.BaseEntity;
import com.spoteditor.backend.image.entity.PlaceImage;
import com.spoteditor.backend.mapping.placelogplacemapping.entity.PlaceLogPlaceMapping;
import com.spoteditor.backend.mapping.placelogtagmapping.entity.PlaceLogTagMapping;
import com.spoteditor.backend.mapping.userplacelogmapping.entity.UserPlaceLogMapping;
import com.spoteditor.backend.place.entity.Address;
import com.spoteditor.backend.user.entity.User;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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
    private List<UserPlaceLogMapping> userPlaceLogMappings = new ArrayList<>();

    @OneToMany(mappedBy = "placeLog", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<PlaceLogPlaceMapping> placeLogPlaceMappings = new ArrayList<>();

    @OneToMany(mappedBy = "placeLog", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<PlaceLogTagMapping> placeLogTagMappings = new ArrayList<>();

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private PlaceImage placeLogImage;

    @Column(name = "address")
    @Embedded
    private Address address;

    @Column(name = "views")
    private long views;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PlaceLogStatus status;

    @Version
    private Long version;

    @Builder
    private PlaceLog(User user, String name, String description, PlaceImage placeLogImage, Address address, PlaceLogStatus status) {
        this.user = user;
        this.name = name;
        this.description = description;
        this.placeLogImage = placeLogImage;
        this.address = address;
        this.views = 0L;
        this.status = status;
    }

    public void update(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
