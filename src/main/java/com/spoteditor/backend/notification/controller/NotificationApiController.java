package com.spoteditor.backend.notification.controller;

import com.spoteditor.backend.config.page.CustomPageRequest;
import com.spoteditor.backend.config.page.CustomPageResponse;
import com.spoteditor.backend.notification.controller.dto.NotificationListDto;
import com.spoteditor.backend.notification.dto.NotificationDto;
import com.spoteditor.backend.notification.repository.NotificationRepository;
import com.spoteditor.backend.notification.service.NotificationService;
import com.spoteditor.backend.user.common.dto.UserIdDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NotificationApiController {

	private final NotificationRepository notificationRepository;
	private final NotificationService notificationService;

	@PostMapping("/notice")
	public ResponseEntity<Void> notice(@AuthenticationPrincipal UserIdDto dto, @RequestBody NotificationDto notificationDto) {

		notificationService.send(notificationDto);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.build();
	}

	@GetMapping("/notice")
	public ResponseEntity<CustomPageResponse<NotificationListDto>> noticeList(@AuthenticationPrincipal UserIdDto dto, CustomPageRequest request) {

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(notificationRepository.notificationList(dto.getId(), request));
	}

	@PutMapping("/notice/{id}")
	public ResponseEntity<Void> read(Long notificationId) {
		notificationService.read(notificationId);
		return ResponseEntity
				.status(HttpStatus.NO_CONTENT)
				.build();
	}

	@PutMapping("/notice/all")
	public ResponseEntity<Void> readAll(@AuthenticationPrincipal UserIdDto dto) {
		notificationService.readAll(dto.getId());
		return ResponseEntity
				.status(HttpStatus.NO_CONTENT)
				.build();
	}
}
