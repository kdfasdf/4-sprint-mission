package com.sprint.mission.discodeit.controller.api;

import com.sprint.mission.discodeit.dto.userstatus.UserStatusResponse;
import com.sprint.mission.discodeit.dto.userstatus.request.UserStatusUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.springframework.http.ResponseEntity;

@Tag(name = "User Status", description = "User Status API")
public interface UserStatusApi {

    @Operation(summary = "User 온라인 상태 업데이트")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "UserStatus 업데이트 성공",
                            content = @Content(schema = @Schema(implementation = UserStatusResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "해당 User의 UserStatus를 찾을 수 없음"
                    )
            }
    )
    ResponseEntity<UserStatusResponse> updateUserStatus(@Parameter(description = "상태를 변경할 User ID") UUID userId,
                                                        @Parameter(description = "변경할 User 온라인 상태 정보") UserStatusUpdateRequest userStatusUpdateRequest);

}
