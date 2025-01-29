package com.spoteditor.backend.domain.user.entity;

import com.spoteditor.backend.domain.user.entity.value.OauthProvider;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "description")
    private String description;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "provider")
    private OauthProvider provider;

    @Column(name = "oauth_user_id")
    private String oauthUserId;

//    @Embedded
//    private Address address;

    @Builder
    public User(String email, String name, String imageUrl, OauthProvider provider, String oauthUserId) {
        this.email = email;
        this.name = name;
        this.imageUrl = imageUrl;
        this.provider = provider;
        this.oauthUserId = oauthUserId;
    }
}