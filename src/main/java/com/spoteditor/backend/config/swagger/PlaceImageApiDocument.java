package com.spoteditor.backend.config.swagger;

import com.spoteditor.backend.image.controller.dto.PreSignedUrlRequest;
import com.spoteditor.backend.image.controller.dto.PreSignedUrlResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

public interface PlaceImageApiDocument {

	@Operation(
			summary = "Pre-signed URL 생성",
			description = "이미지 업로드를 위한 pre-signed URL을 생성합니다."
	)
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "201",
					description = "Pre-signed URL 생성 성공",
					content = @Content(
							mediaType = "application/json",
							schema = @Schema(implementation = PreSignedUrlResponse.class)
					)
			),
			@ApiResponse(
					responseCode = "400",
					description = "잘못된 요청",
					content = @Content
			)
	})
	ResponseEntity<PreSignedUrlResponse> processPreSignedUrlResponse(
			@Parameter(
					description = "원본 파일명",
					required = true,
					schema = @Schema(implementation = PreSignedUrlRequest.class)
			) PreSignedUrlRequest request
	);
}