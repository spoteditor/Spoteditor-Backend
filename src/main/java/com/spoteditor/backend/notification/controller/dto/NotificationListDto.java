package com.spoteditor.backend.notification.controller.dto;

import com.spoteditor.backend.notification.entity.NotificationType;

import java.time.LocalDateTime;

public record NotificationListDto(
	Long id,
	String imageUrl,
	String message,
	NotificationType type,
	LocalDateTime createdAt,
	boolean isRead
) {
}
