package com.spoteditor.backend.notification.controller;

import com.spoteditor.backend.notification.config.NotificationSubscriber;
import com.spoteditor.backend.notification.dto.NotificationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WebSocketHandler {

	private final SimpMessagingTemplate messagingTemplate;

	@MessageMapping("/send")
	public void sendNotificationMessage(NotificationDto notificationDto) {
		log.info("[#] message = " + notificationDto.message());
		messagingTemplate.convertAndSend(NotificationSubscriber.CLIENT_URL + notificationDto.to(), notificationDto.message());
	}
}
