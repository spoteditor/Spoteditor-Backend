package com.spoteditor.backend.place.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spoteditor.backend.config.page.CustomPageRequest;
import com.spoteditor.backend.config.page.CustomPageResponse;
import com.spoteditor.backend.place.controller.dto.PlaceResponse;
import com.spoteditor.backend.place.entity.Place;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.spoteditor.backend.image.entity.QPlaceImage.placeImage;
import static com.spoteditor.backend.place.entity.QPlace.place;

@Repository
@RequiredArgsConstructor
public class PlaceRepositoryImpl implements PlaceRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public CustomPageResponse<PlaceResponse> findAllPlace(CustomPageRequest request) {
		PageRequest pageRequest = request.of();

		List<PlaceResponse> placeList = queryFactory
				.select(Projections.constructor(PlaceResponse.class,
						place.id.as("placeId"),
						place.user.name.as("author"),
						place.name,
						place.description,
						place.address,
						place.category))
				.from(place)
				.offset(pageRequest.getOffset())
				.limit(pageRequest.getPageSize())
				.orderBy(place.createdAt.asc())
				.fetch();

		JPAQuery<Long> queryCount = queryFactory
				.select(place.count())
				.from(place);

		Page<PlaceResponse> page = PageableExecutionUtils.getPage(
				placeList,
				pageRequest,
				queryCount::fetchOne
		);

		return new CustomPageResponse<>(page);
	}

	@Override
	public List<Place> findAllPlacesByUserId(Long userId) {
		return queryFactory
				.selectFrom(place)
				.leftJoin(place.placeImages, placeImage).fetchJoin()
				.where(place.user.id.eq(userId))
				.distinct()
				.fetch();
	}
}
