package com.spoteditor.backend.notification.dto;

import com.spoteditor.backend.notification.entity.Notification;
import com.spoteditor.backend.notification.entity.NotificationType;
import com.spoteditor.backend.user.entity.User;
import lombok.Builder;

@Builder
public record NotificationDto(
		User fromUser,
		User toUser,
		NotificationType type,
		String message
) {

	public Notification toEntity() {
		return Notification.builder()
				.message(message)
				.type(type)
				.fromUser(fromUser)
				.toUser(toUser)
				.build();
	}
}
