package com.sprint.mission.discodeit.controller.api;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;

@Tag(name = "BinaryContent", description = "BinaryContent API")
public interface BinaryContentApi {

    @Operation(summary = "BinaryContent 조회")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "BinaryContent 조회 성공",
                    content = @Content(schema = @Schema(implementation = BinaryContentResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404", description ="BinaryContent not found"
            )
    })
    ResponseEntity<BinaryContentResponse> getBinaryContent(
            @Parameter(description = "binaryContentId") UUID binaryContentId
    );

    @Operation(summary = "BinaryContents 조회")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "BinaryContents 조회 성공",
                    content = @Content(schema = @Schema(implementation = BinaryContentResponse.class))
            )
    })
    ResponseEntity<List<BinaryContentResponse>> getBinaryContents(
            @Parameter(description = "binaryContentId") List<UUID> binaryContentsIds);
}
