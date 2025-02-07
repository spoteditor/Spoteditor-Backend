package com.spoteditor.backend.notification.config;

import com.spoteditor.backend.notification.dto.NotificationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationPublisher {

	private final RabbitTemplate rabbitTemplate;

	public void announcementPublish(NotificationDto dto) {
		rabbitTemplate.convertAndSend(RabbitMqConfiguration.FANOUT_EXCHANGE_NAME, "", dto);
		log.info("[#] Published Announcement Notification: " + dto);
	}

	public void followPublish(NotificationDto dto) {
		rabbitTemplate.convertAndSend(RabbitMqConfiguration.DIRECT_EXCHANGE_NAME, "", dto);
		log.info("[#} Published Follow Notification: " + dto);
	}
}
