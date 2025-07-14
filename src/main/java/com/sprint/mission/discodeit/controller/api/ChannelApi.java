package com.sprint.mission.discodeit.controller.api;

import com.sprint.mission.discodeit.dto.channel.ChannelResponse;
import com.sprint.mission.discodeit.dto.channel.request.ChannelCreateRequest;
import com.sprint.mission.discodeit.dto.channel.request.ChannelUpdateRequest;
import com.sprint.mission.discodeit.dto.channel.request.PrivateChannelCreateRequest;
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

@Tag(name = "Channel", description = "Channel API")
public interface ChannelApi {

    @Operation(summary = "Public Channel 생성")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201", description = "Public Channel 생성 성공",
                    content = @Content(schema = @Schema(implementation = ChannelResponse.class))
            )
    })
    ResponseEntity<ChannelResponse> createPublicChannel(
        @Parameter(
                description = "Public Channel 생성 요청") ChannelCreateRequest request
            );


    @Operation(summary = "Private Channel 생성")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Private Channel 생성 성공",
                    content = @Content(schema = @Schema(implementation = ChannelResponse.class))
            )
    })
    ResponseEntity<ChannelResponse> createPrivateChannel(
        @Parameter(
                description = "Private Channel 생성 요청") PrivateChannelCreateRequest request
            );


    @Operation(summary = "Channel 수정")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Channel 수정 성공",
                    content = @Content(schema = @Schema(implementation = ChannelResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404", description = "Channel not found"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Private Channel은 수정할 수 없음"
            )
    })
    ResponseEntity<ChannelResponse> updateChannel(
        @Parameter(description = "Channel Id") UUID channelId,
        @Parameter(description = "Channel 수정 요청") ChannelUpdateRequest request
    );


    @Operation(summary = "Channel 조회")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Channel 조회 성공",
                    content = @Content(schema = @Schema(implementation = ChannelResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404", description = "Channel not found"
            )
    })
    ResponseEntity<ChannelResponse> findChannelById(@Parameter(description = "Channel Id") UUID channelId);


    @Operation(summary = "유저 참여 Channel 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "유저 참여 Channel 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = ChannelResponse.class))
            )
    })
    ResponseEntity<List<ChannelResponse>> findAllChannelsByUserId(@Parameter(description = "유저 Id") UUID userId);


    @Operation(summary = "Channel 삭제")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Channel 삭제 성공"
            ),
            @ApiResponse(
                    responseCode = "404", description = "Channel not found"
            )
    })
    ResponseEntity<Void> deleteChannel(@Parameter(description = "Channel Id") UUID channelId);
}
