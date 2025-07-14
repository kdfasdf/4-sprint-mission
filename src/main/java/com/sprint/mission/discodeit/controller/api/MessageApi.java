package com.sprint.mission.discodeit.controller.api;

import com.sprint.mission.discodeit.dto.message.MessageResponse;
import com.sprint.mission.discodeit.dto.message.request.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.message.request.MessageUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Message", description = "Message API")
public interface MessageApi {

    @Operation(summary = "Message 생성")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201", description = "Message 생성 성공",
                    content = @Content(schema = @Schema(implementation = MessageResponse.class))
            )
    })
    ResponseEntity<MessageResponse> createMessage(
            @Parameter(
                    description = "Message 생성 요청",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ) MessageCreateRequest request,
            @Parameter(
                    description = "Message에 포함된 파일",
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
            ) List<MultipartFile> attachments
    );


    @Operation(summary = "Message 수정")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "Message 수정 성공",
                    content = @Content(schema = @Schema(implementation = MessageResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404", description = "Message not found"
            )
    })
    ResponseEntity<MessageResponse> updateMessage(@Parameter(description = "Message Id") UUID messageId, @Parameter(description = "Message 수정 요청") MessageUpdateRequest request);


    @Operation(summary = "Message 삭제")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Message 삭제 성공"
            ),
            @ApiResponse(
                    responseCode = "404", description = "Message not found"
            )
    })
    ResponseEntity<Void> deleteMessage(@Parameter(description = "Message Id") UUID messageId);


    @Operation(summary = "Channel의 Message 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Channel의 Message 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = MessageResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404", description = "Channel not found"
            )
    })
    ResponseEntity<List<MessageResponse>> findMessagesByChannelId(@Parameter(description = "Channel Id") UUID channelId);
}
