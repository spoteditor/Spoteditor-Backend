package com.spoteditor.backend.notification.entity;

public enum NotificationType {

	FOLLOW("팔로우 알림");

	private final String description;

	NotificationType(String description) {
		this.description = description;
	}
}
