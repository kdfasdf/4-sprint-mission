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

    @Operation(summary = "UserStatus 수정")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "UserStatus 업데이트 성공",
                            content = @Content(schema = @Schema(implementation = UserStatusResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "UserStatus not found"
                    )
            }
    )
    ResponseEntity<UserStatusResponse> updateUserStatus(@Parameter(description = "수정할 UserStatus Id") UUID userId, @Parameter(description = "UserStatus 수정 요청") UserStatusUpdateRequest userStatusUpdateRequest);

}
