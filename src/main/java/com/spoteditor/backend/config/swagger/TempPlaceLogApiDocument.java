package com.spoteditor.backend.config.swagger;

import com.spoteditor.backend.global.response.ErrorCode;
import com.spoteditor.backend.global.response.ErrorResponse;
import com.spoteditor.backend.placelog.controller.dto.PlaceLogResponse;
import com.spoteditor.backend.placelog.controller.dto.TempPlaceLogRegisterResponse;
import com.spoteditor.backend.placelog.service.dto.TempPlaceLogRegisterCommand;
import com.spoteditor.backend.user.common.dto.UserIdDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import static com.spoteditor.backend.global.response.ErrorCode.TEMP_PLACE_LOG_ALREADY_EXIST;

@Tag(name = "temp-place-log", description = "임시저장 로그 API")
public interface TempPlaceLogApiDocument {

    @Operation(
            summary = "임시저장 로그 생성",
            description = "태그 등록시 임시저장 로그 자동생성",
            method = "POST"
    )
    @RequestBody(
            description = "태그 등록",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TempPlaceLogRegisterCommand.class),
                    examples = @ExampleObject(
                            name = "TempPlaceLogRegisterRequest",
                            value = """
                                    {
                                        "tags" : [
                                            {
                                                "name": "혼자",
                                                "category": "WITH_WHOM"
                                            },
                                            {
                                                "name": "친구랑",
                                                "category": "WITH_WHOM"
                                            },
                                            {
                                                "name": "SNS 핫플레이스",
                                                "category": "MOOD"
                                            },
                                            {
                                                "name": "체험·액티비티",
                                                "category": "MOOD"
                                            }
                                        ]
                                    }
                                    """
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "CREATED",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TempPlaceLogRegisterResponse.class),
                            examples = @ExampleObject(
                                    name = "임시저장 등록 성공",
                                    value = """
                                            {
                                                "tempPlaceLogId": 1
                                            }
                                            """
                            )
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
                                            name = "이미 임시저장이 있을 떄",
                                            value = """
                                                    {
                                                        "status": "TEMP_PLACE_LOG_ALREADY_EXIST",
                                                        "code": "PL004",
                                                        "message": "임시저장은 1개까지 등록 가능합니다.",
                                                        "timestamp": "2025-02-17T16:50:36.569347"
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "Tag등록 5개초과일 때",
                                            value = """
                                                    {
                                                         "status": "TAG_LIMIT_EXCEEDED",
                                                         "code": "T002",
                                                         "message": "태그는 최대 5개까지 등록 가능합니다.",
                                                         "timestamp": "2025-02-17T16:50:36.569347"
                                                     }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "Tag가 유효하지 않을 때",
                                            value = """
                                                    {
                                                        "status": "INVALID_TAG",
                                                        "code": "T003",
                                                        "message": "존재하지 않는 태그입니다.",
                                                        "timestamp": "2025-02-17T16:50:36.569347"
                                                    }
                                                    """
                                    )
                            }
                    )
            )
    })
    ResponseEntity<TempPlaceLogRegisterResponse> saveTempPlaceLogTag(
            @Parameter(
                    description = "인증된 사용자 정보",
                    required = true
            ) UserIdDto userIdDto,
            @Parameter(
                    description = "임시저장할 태그 정보",
                    required = true,
                    schema = @Schema(implementation = TempPlaceLogRegisterCommand.class
            )) TempPlaceLogRegisterCommand command
    );

    @Operation(
            summary = "임시저장 로그 가져오기",
            description = "임시저장 로그 가져오기",
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
                                            name = "임시저장 로그가 퍼블리싱 되었을 때",
                                            value = """
                                                    {
                                                        "status": "NOT_TEMP_PLACE_LOG",
                                                        "code": "PL007",
                                                        "message": "해당 로그가 임시저장중이 아닙니다.",
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
                                            name = "임시저장 로그 소유자가 아닐 때",
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
    ResponseEntity<PlaceLogResponse> getTempPlaceLog(
            @Parameter(
                    description = "인증된 사용자 정보",
                    required = true
            ) UserIdDto userIdDto,
            @PathVariable Long placeLogId
    );
}
