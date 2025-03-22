package com.spoteditor.backend.user.entity;

import com.spoteditor.backend.global.common.BaseEntity;
import com.spoteditor.backend.image.entity.PlaceImage;
import com.spoteditor.backend.mapping.userplacelogmapping.entity.UserPlaceLogMapping;
import com.spoteditor.backend.place.entity.Place;
import com.spoteditor.backend.user.service.dto.UserUpdateCommand;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<UserPlaceLogMapping> userPlaceLogMappings = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Place> userPlace = new ArrayList<>();

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private PlaceImage uploadImage;

    @Column(name = "description")
    private String description;

    @Column(name = "instagram_id")
    private String instagramId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private UserRole role;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "provider")
    private OauthProvider provider;

    @Column(name = "oauth_user_id")
    private String oauthUserId;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @Builder
    private User(String email, String name, String imageUrl, OauthProvider provider, String oauthUserId, UserRole role) {
        this.email = email;
        this.name = name;
        this.imageUrl = imageUrl;
        this.provider = provider;
        this.oauthUserId = oauthUserId;
        this.role = role;
    }

    public void update(String name, String description, String instagramId) {
        if (name != null && !name.trim().isEmpty()) this.name = name;
        if (description != null) this.description = description;
        if (instagramId != null) this.instagramId = instagramId;
    }

    public void deleteImage() {
        if(this.uploadImage != null) {
            this.uploadImage = null;
        }
    }

    public void addImage(PlaceImage placeImage) {
        this.uploadImage = placeImage;
    }

    public void softDelete() {
        this.isDeleted = true;
    }
}