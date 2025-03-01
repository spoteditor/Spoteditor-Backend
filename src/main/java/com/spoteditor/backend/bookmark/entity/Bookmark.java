package com.spoteditor.backend.bookmark.entity;

import com.spoteditor.backend.global.common.BaseEntity;
import com.spoteditor.backend.place.entity.Place;
import com.spoteditor.backend.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name = "bookmark")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bookmark_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "place_id")
	private Place place;

	@Builder
	private Bookmark(User user, Place place) {
		this.user = user;
		this.place = place;
	}
}
