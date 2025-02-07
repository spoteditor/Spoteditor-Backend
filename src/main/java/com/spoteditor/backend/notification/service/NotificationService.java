package com.spoteditor.backend.notification.service;

import com.spoteditor.backend.notification.config.NotificationPublisher;
import com.spoteditor.backend.notification.dto.NotificationDto;
import com.spoteditor.backend.notification.entity.Notification;
import com.spoteditor.backend.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

	private final NotificationRepository notificationRepository;
	private final NotificationPublisher notificationPublisher;

	public void send(NotificationDto dto) {

		switch (dto.type()) {
			case ANNOUNCEMENT -> notificationPublisher.announcementPublish(dto);
			case FOLLOW -> notificationPublisher.followPublish(dto);
		}

		Notification notification = dto.toEntity();
		notificationRepository.save(notification);
	}
}
