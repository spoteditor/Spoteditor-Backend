package com.spoteditor.backend.notification.repository;

import com.spoteditor.backend.notification.controller.dto.NotificationListDto;
import com.spoteditor.backend.notification.entity.Notification;

import java.util.List;

public interface NotificationRepositoryCustom {

	List<NotificationListDto> notificationList(Long userId);
	List<Notification> findAllByUserIdAndUnread(Long userId);
	List<Notification> findAllByUserIdAndRead(Long userId);
	void updateNotificationRead(Long userId);
}
