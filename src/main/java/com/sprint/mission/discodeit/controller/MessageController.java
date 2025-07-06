package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.ApiResponse;
import com.sprint.mission.discodeit.dto.message.MessageResponse;
import com.sprint.mission.discodeit.dto.message.request.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.message.request.MessageUpdateRequest;
import com.sprint.mission.discodeit.service.MessageService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/messages")
    public ResponseEntity<ApiResponse<MessageResponse>> createMessage(@RequestBody MessageCreateRequest request) {
        return ResponseEntity.ok().body(ApiResponse.onSuccess(messageService.createMessage(request.toServiceRequest())));
    }

    @PatchMapping( "/messages/{messageId}")
    public ResponseEntity<ApiResponse<MessageResponse>> updateMessage(@PathVariable("messageId") UUID messageId, @RequestBody MessageUpdateRequest request) {
        return ResponseEntity.ok().body(ApiResponse.onSuccess(messageService.updateContent(request.toServiceRequest(messageId))));
    }

    @DeleteMapping( "/messages/{messageId}")
    public ResponseEntity<ApiResponse<Void>> deleteMessage(@PathVariable("messageId") UUID messageId) {
        messageService.deleteMessage(messageId);
        return ResponseEntity.ok().body( ApiResponse.onSuccess(null));
    }

    @GetMapping( "/channels/{channelId}/messages")
    public ResponseEntity<List<MessageResponse>> findMessagesByChannelId(@PathVariable("channelId") UUID channelId) {
        return ResponseEntity.ok(messageService.findMessagesByChannelId(channelId));
    }

}
