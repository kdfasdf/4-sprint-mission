package com.sprint.mission.discodeit.api.controller;

import com.sprint.mission.discodeit.dto.message.MessageResponse;
import com.sprint.mission.discodeit.dto.message.request.MessageCreateServiceRequest;
import com.sprint.mission.discodeit.dto.message.request.MessageUpdateRequest;
import com.sprint.mission.discodeit.service.MessageService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MessageController {

    private final MessageService messageService;

    @RequestMapping(method = RequestMethod.POST, value = "/messages")
    public ResponseEntity<Void> createMessage(MessageCreateServiceRequest request) {
        messageService.createMessage(request);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/messages/{messageId}")
    public ResponseEntity<MessageResponse> updateMessage(@PathVariable UUID messageId, @RequestBody MessageUpdateRequest request) {
        return ResponseEntity.ok(messageService.updateContent(request.toServiceRequest(messageId)));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/messages/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable UUID messageId) {
        messageService.deleteMessage(messageId);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/messages/channels/{channelId}")
    public ResponseEntity<List<MessageResponse>> findMessagesByChannelId(@PathVariable UUID channelId) {
        return ResponseEntity.ok(messageService.findMessagesByChannelId(channelId));
    }

}
