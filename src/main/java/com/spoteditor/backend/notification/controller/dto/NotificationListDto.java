package com.spoteditor.backend.notification.controller.dto;

import com.spoteditor.backend.notification.entity.NotificationType;

public record NotificationListDto(
		Long id,
		Long userId,
		String message,
		String redirectUrl,
		NotificationType type
) {
}
