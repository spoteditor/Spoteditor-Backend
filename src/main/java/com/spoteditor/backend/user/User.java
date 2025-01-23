package com.spoteditor.backend.user;

import com.spoteditor.backend.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "USERS")
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	private String email;
	private String provider;
	private String name;
	private String imageUrl;
	private String description;
}
