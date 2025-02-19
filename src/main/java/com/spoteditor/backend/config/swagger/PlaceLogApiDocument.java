package com.spoteditor.backend.config.swagger;

import com.spoteditor.backend.config.page.CustomPageRequest;
import com.spoteditor.backend.config.page.CustomPageResponse;
import com.spoteditor.backend.global.response.ErrorResponse;
import com.spoteditor.backend.placelog.controller.dto.PlaceLogPlaceRequest;
import com.spoteditor.backend.placelog.controller.dto.PlaceLogResponse;
import com.spoteditor.backend.placelog.controller.dto.TempPlaceLogRegisterResponse;
import com.spoteditor.backend.placelog.service.dto.TempPlaceLogRegisterCommand;
import com.spoteditor.backend.user.common.dto.UserIdDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface PlaceLogApiDocument {

    @Operation(
            summary = "임시저장 로그 퍼블리싱",
            description = "임시저장 로그 퍼블리싱",
            method = "POST"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "BAD_REQUEST",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "해당 Place Log가 없을 때",
                                            value = """
                                                    {
                                                        "status": "NOT_FOUND_PLACE_LOG",
                                                        "code": "PL005",
                                                        "message": "로그를 찾을 수 없습니다.",
                                                        "timestamp": "2025-02-17T16:50:36.569347"
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "로그에 제목이 없을 때",
                                            value = """
                                                    {
                                                        "status": "NO_PLACE_LOG_NAME",
                                                        "code": "PL009",
                                                        "message": "저장하려면 제목이 필요합니다",
                                                        "timestamp": "2025-02-17T16:50:36.569347"
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "로그에 장소가 등록되지 않았을 때",
                                            value = """
                                                    {
                                                        "status": "PLACE_MINIMUM_REQUIRED",
                                                        "code": "PL002",
                                                        "message": "로그에 장소는 최소 1개 이상 등록해야 합니다.",
                                                        "timestamp": "2025-02-17T16:50:36.569347"
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "로그에 장소가 10개 초과로 등록되었을 때",
                                            value = """
                                                    {
                                                        "status": "PLACE_LIMIT_EXCEEDED",
                                                        "code": "PL003",
                                                        "message": "로그에 장소는 최대 10개까지 등록 가능합니다.",
                                                        "timestamp": "2025-02-17T16:50:36.569347"
                                                    }
                                                    """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "FORBIDDEN",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "퍼블리싱 로그 소유자가 아닐 때",
                                            value = """
                                                    {
                                                        "status": "NOT_PLACE_LOG_OWNER",
                                                        "code": "PL006",
                                                        "message": "로그 소유자가 아닙니다.",
                                                        "timestamp": "2025-02-17T16:50:36.569347"
                                                    }
                                                    """
                                    )
                            }
                    )
            )
    })
    ResponseEntity<Void> publishPlaceLog(
            @Parameter(
                    description = "인증된 사용자 정보",
                    required = true
            ) UserIdDto userIdDto,
            @PathVariable Long placeLogId
    );

    @Operation(
            summary = "로그 리스트 가져오기",
            description = "로그 리스트 가져오기",
            method = "GET"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @Content(schema = @Schema(implementation = CustomPageResponse.class))
            )
    })
    ResponseEntity<CustomPageResponse<PlaceLogResponse>> getPlaceLogs(
            @Parameter(
                    description = "페이지 요청 정보 (page, size)",
                    required = true
            ) CustomPageRequest pageRequest
    );

    @Operation(
            summary = "로그 수정",
            description = "로그 수정",
            method = "PATCH"
    )
    @RequestBody(
            description = "이름, 설명, 장소변경",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PlaceLogPlaceRequest.class),
                    examples = @ExampleObject(
                            name = "PlaceLogPlaceRequest",
                            value = """
                                    {
                                         "name" : "테스트 로그 이름",
                                         "description" : "테스트 로그 설명",
                                         "places" : [
                                             {
                                                 "name" : "장소이름",
                                                 "description" : "장소 설명",
                                                 "originalFile" : "망원시장",
                                                 "uuid" : "af3fbd7b-a395-4df0-a9fe-39485d86e731",
                                                 "address" : {
                                                     "address" : "주소",
                                                     "roadAddress" : "도로명주소",
                                                     "latitude" : 34.123,
                                                     "longitude" : 23.123,
                                                     "sido" : "시도",
                                                     "bname" : "이름",
                                                     "sigungu" : "시군구"
                                                 },
                                                 "category" : "TOUR"
                                             }
                                         ]
                                    }
                                    """
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PlaceLogResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "BAD_REQUEST",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "해당 Place Log가 없을 때",
                                            value = """
                                                    {
                                                        "status": "NOT_FOUND_PLACE_LOG",
                                                        "code": "PL005",
                                                        "message": "로그를 찾을 수 없습니다.",
                                                        "timestamp": "2025-02-17T16:50:36.569347"
                                                    }
                                                    """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "FORBIDDEN",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "로그 소유자가 아닐 때",
                                            value = """
                                                    {
                                                        "status": "NOT_PLACE_LOG_OWNER",
                                                        "code": "PL006",
                                                        "message": "로그 소유자가 아닙니다.",
                                                        "timestamp": "2025-02-17T16:50:36.569347"
                                                    }
                                                    """
                                    )
                            }
                    )
            )
    })
    ResponseEntity<PlaceLogResponse> updatePlaceLog(
            @Parameter(
                    description = "인증된 사용자 정보",
                    required = true
            ) UserIdDto userIdDto,
            @PathVariable Long placeLogId,
            @Parameter(
                    description = "변경하려는 로그 정보",
                    required = true
            ) PlaceLogPlaceRequest request
    );

    @Operation(
            summary = "단일 로그 가져오기",
            description = "단일 로그 가져오기",
            method = "GET"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PlaceLogResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "BAD_REQUEST",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "해당 Place Log가 없을 때",
                                            value = """
                                                    {
                                                        "status": "NOT_FOUND_PLACE_LOG",
                                                        "code": "PL005",
                                                        "message": "로그를 찾을 수 없습니다.",
                                                        "timestamp": "2025-02-17T16:50:36.569347"
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "해당 로그가 퍼블리시중이 아닐 때",
                                            value = """
                                                    {
                                                        "status": "NOT_PUBLISHED_PLACE_LOG",
                                                        "code": "PL008",
                                                        "message": "해당 로그가 퍼블리시중이 아닙니다",
                                                        "timestamp": "2025-02-17T16:50:36.569347"
                                                    }
                                                    """
                                    )
                            }
                    )
            )
    })
    ResponseEntity<PlaceLogResponse> getPlaceLog(
            @Parameter(
                    description = "인증된 사용자 정보",
                    required = true
            ) UserIdDto userIdDto,
            @PathVariable Long placeLogId
    );

    @Operation(
            summary = "단일 로그 삭제",
            description = "단일 로그 삭제",
            method = "DELETE"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "NO_CONTENT",
                    content = @Content(
                            mediaType = "application/json"
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "BAD_REQUEST",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "해당 로그가 없을 때",
                                            value = """
                                                    {
                                                        "status": "NOT_FOUND_PLACE_LOG",
                                                        "code": "PL005",
                                                        "message": "로그를 찾을 수 없습니다.",
                                                        "timestamp": "2025-02-17T16:50:36.569347"
                                                    }
                                                    """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "FORBIDDEN",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "퍼블리싱 로그 소유자가 아닐 때",
                                            value = """
                                                    {
                                                        "status": "NOT_PLACE_LOG_OWNER",
                                                        "code": "PL006",
                                                        "message": "로그 소유자가 아닙니다.",
                                                        "timestamp": "2025-02-17T16:50:36.569347"
                                                    }
                                                    """
                                    )
                            }
                    )
            )
    })
    ResponseEntity<Void> removePlaceLog(
            @Parameter(
                    description = "인증된 사용자 정보",
                    required = true
            ) UserIdDto userIdDto,
            @PathVariable Long placeLogId
    );
}
