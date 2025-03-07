package com.spoteditor.backend.notification.dto;

import com.spoteditor.backend.notification.entity.NotificationType;
import com.spoteditor.backend.user.entity.User;
import lombok.Builder;

@Builder
public record NotificationDto(
		Long from,
		Long to,
		NotificationType type,
		String message
) {

	public static NotificationDto from(User fromUser, User toUser, NotificationType type, String message) {
		return new NotificationDto(fromUser.getId(), toUser.getId(), type, message);
	}
}
