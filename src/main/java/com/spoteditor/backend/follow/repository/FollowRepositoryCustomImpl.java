package com.spoteditor.backend.follow.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spoteditor.backend.config.page.CustomPageRequest;
import com.spoteditor.backend.config.page.CustomPageResponse;
import com.spoteditor.backend.follow.controller.dto.FollowResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.spoteditor.backend.follow.entity.QFollow.follow;

@Repository
@RequiredArgsConstructor
public class FollowRepositoryCustomImpl implements FollowRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public CustomPageResponse<FollowResponse> findAllFollower(Long userId, CustomPageRequest request) {

		PageRequest pageRequest = request.of();

		List<FollowResponse> content = queryFactory
				.select(Projections.constructor(FollowResponse.class,
						follow.follower.id,
						follow.follower.name,
						follow.follower.email,
						follow.follower.imageUrl
				))
				.from(follow)
				.where(follow.following.id.eq(userId))
				.offset(pageRequest.getOffset())
				.limit(pageRequest.getPageSize())
				.fetch();

		JPAQuery<Long> queryCount = queryFactory
				.select(follow.count())
				.from(follow)
				.where(follow.following.id.eq(userId));

		Page<FollowResponse> page = PageableExecutionUtils.getPage(
				content,
				pageRequest,
				queryCount::fetchOne
		);

		return new CustomPageResponse<>(page);
	}

	public CustomPageResponse<FollowResponse> findAllFollowing(Long userId, CustomPageRequest request) {

		PageRequest pageRequest = request.of();

		List<FollowResponse> content = queryFactory
				.select(Projections.constructor(FollowResponse.class,
						follow.following.id,
						follow.following.name,
						follow.following.email,
						follow.following.imageUrl
				))
				.from(follow)
				.where(follow.follower.id.eq(userId))
				.offset(pageRequest.getOffset())
				.limit(pageRequest.getPageSize())
				.fetch();

		JPAQuery<Long> queryCount = queryFactory
				.select(follow.count())
				.from(follow)
				.where(follow.follower.id.eq(userId));

		Page<FollowResponse> page = PageableExecutionUtils.getPage(
				content,
				pageRequest,
				queryCount::fetchOne
		);

		return new CustomPageResponse<>(page);
	}

	public long countFollower(Long userId) {
		Long count = queryFactory
				.select(follow.count())
				.from(follow)
				.where(follow.following.id.eq(userId))
				.fetchOne();

		return count != null ? count : 0L;
	}

	public long countFollowing(Long userId) {
		Long count = queryFactory
				.select(follow.count())
				.from(follow)
				.where(follow.follower.id.eq(userId))
				.fetchOne();

		return count != null ? count : 0L;
	}


}
