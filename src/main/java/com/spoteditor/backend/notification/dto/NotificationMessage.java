package com.spoteditor.backend.notification.dto;

public class NotificationMessage {

	private static final String PREFIX = "[#] 알림: ";

	public static final String FOLLOW = PREFIX + "%s님이 %s님을 팔로우했습니다.";

	public static String formatFollowMessage(String followerName, String followingName) {
		return String.format(FOLLOW, followerName, followingName);
	}
}
