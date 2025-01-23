package com.spoteditor.backend.place.entity;

import com.spoteditor.backend.global.common.BaseEntity;
import com.spoteditor.backend.image.entity.PlaceImage;
import com.spoteditor.backend.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "PLACE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "place_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "name", unique = true)
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "address")
	@Embedded
	private Address address;

	@Column(name = "category")
	@Enumerated(EnumType.STRING)
	private Category category;

	@Column(name = "bookmark")
	private int bookmark;

	@OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PlaceImage> placeImages = new ArrayList<>();

	@Version
	private Long version;

	@Builder
	private Place(User user, Category category, Address address, String description, String name, int bookmark) {
		this.user = user;
		this.address = address;
		this.description = description;
		this.name = name;
		this.bookmark = bookmark;
		this.category = category;
	}

	public void increaseBookmark() {
		this.bookmark++;
	}

	public void decreaseBookmark() {
		if (this.bookmark <= 0) {
			throw new IllegalArgumentException("북마크 개수는 0 미만의 값을 가질 수 없습니다.");
		}
		this.bookmark--;
	}

	public void update(String name, String description, Category category) {
		this.name = name;
		this.description = description;
		this.category = category;
	}
}
