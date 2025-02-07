package com.spoteditor.backend.notification.repository;

import com.spoteditor.backend.config.page.CustomPageRequest;
import com.spoteditor.backend.config.page.CustomPageResponse;
import com.spoteditor.backend.notification.controller.dto.NotificationListDto;

public interface NotificationRepositoryCustom {

	CustomPageResponse<NotificationListDto> notificationList(Long userId, CustomPageRequest request);
}
