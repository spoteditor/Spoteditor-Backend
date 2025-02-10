package com.spoteditor.backend.notification.config;

import com.spoteditor.backend.notification.dto.NotificationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationSubscriber {

	public static final String CLIENT_URL = "/topic/notifications";

	private final SimpMessagingTemplate template;

	@RabbitListener(queues = RabbitMqConfiguration.FOLLOW_QUEUE)
	public void subscribeFollow(NotificationDto dto) {
		log.info("Received Notification: " + dto);
		template.convertAndSend(CLIENT_URL, dto);
	}

	@RabbitListener(queues = RabbitMqConfiguration.ANNOUNCEMENT_QUEUE)
	public void subscribeAnnouncement(NotificationDto dto) {
		log.info("Received Notification: " + dto);
		template.convertAndSend(CLIENT_URL, dto);
	}
}
