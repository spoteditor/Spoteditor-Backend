package com.spoteditor.backend.notification.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spoteditor.backend.config.page.CustomPageRequest;
import com.spoteditor.backend.config.page.CustomPageResponse;
import com.spoteditor.backend.notification.controller.dto.NotificationListDto;
import com.spoteditor.backend.notification.entity.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.spoteditor.backend.notification.entity.QNotification.notification;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryCustomImpl implements NotificationRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public CustomPageResponse<NotificationListDto> notificationList(Long userId, CustomPageRequest request) {

		PageRequest pageRequest = request.of();

		List<NotificationListDto> content = queryFactory
				.select(Projections.constructor(NotificationListDto.class,
						notification.id,
						notification.toUser.id,
						notification.message,
						notification.redirectUrl,
						notification.notificationType
				))
				.from(notification)
				.where(notification.toUser.id.eq(userId))
				.offset(pageRequest.getOffset())
				.limit(pageRequest.getPageSize())
				.fetch();

		JPAQuery<Long> querycount = queryFactory
				.select(notification.count())
				.from(notification);

		Page<NotificationListDto> page = PageableExecutionUtils.getPage(
				content,
				pageRequest,
				querycount::fetchOne
		);

		return new CustomPageResponse<>(page);
	}

	@Override
	public List<Notification> findAllByUserIdAndUnread(Long userId) {
		return queryFactory
				.selectFrom(notification)
				.where(notification.toUser.id.eq(userId), notification.isRead.eq(false))
				.orderBy(notification.toUser.id.desc())
				.fetch();
	}

	@Override
	public List<Notification> findAllByUserIdAndRead(Long userId) {
		return queryFactory
				.selectFrom(notification)
				.where(notification.toUser.id.eq(userId), notification.isRead.eq(true))
				.orderBy(notification.toUser.id.desc())
				.fetch();
	}

	@Modifying(clearAutomatically = true)
	@Override
	public void updateNotificationRead(Long userId) {
		queryFactory.update(notification)
				.set(notification.isRead, true)
				.where(notification.toUser.id.eq(userId))
				.execute();
	}

}
