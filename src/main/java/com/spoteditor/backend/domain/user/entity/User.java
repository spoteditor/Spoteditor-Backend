package com.spoteditor.backend.domain.user.entity;

import com.spoteditor.backend.domain.user.entity.value.LoginStatus;
import com.spoteditor.backend.domain.user.entity.value.OauthProvider;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
public class User {
    @Id
    private Long id;

    private String email;

    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    private String description;

    @Column(name = "login_status")
    private LoginStatus loginStatus;

    @Column(name = "provider")
    private OauthProvider provider;

    @Column(name = "oauth_user_id")
    private String oauthUserId;

//    @Embedded
//    private Address address;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}