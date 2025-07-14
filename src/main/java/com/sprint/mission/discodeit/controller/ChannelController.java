package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.channel.ChannelResponse;
import com.sprint.mission.discodeit.dto.channel.request.ChannelCreateRequest;
import com.sprint.mission.discodeit.dto.channel.request.ChannelUpdateRequest;
import com.sprint.mission.discodeit.dto.channel.request.PrivateChannelCreateRequest;
import com.sprint.mission.discodeit.service.ChannelService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChannelController {

    private final ChannelService channelService;

    @PostMapping( "/channels/public")
    public ResponseEntity<ChannelResponse> createPublicChannel(@RequestBody ChannelCreateRequest request) {
        return ResponseEntity.ok().body(channelService.createPublicChannel(request.toServiceRequest()));
    }

    @PostMapping("/channels/private")
    public ResponseEntity<ChannelResponse> createPrivateChannel(@RequestBody PrivateChannelCreateRequest request) {
        return ResponseEntity.ok().body(channelService.createPrivateChannel(request.toServiceRequest()));
    }

    @GetMapping( "/channels/{channelId}")
    public ResponseEntity<ChannelResponse> findChannelById(@PathVariable("channelId") UUID channelId) {
        return ResponseEntity.ok().body(channelService.findChannelById(channelId));
    }

    @GetMapping( "/channels")
    public ResponseEntity<List<ChannelResponse>> findAllChannelsByUserId(@RequestParam("userId") UUID userId) {
        return ResponseEntity.ok().body(channelService.findAllChannelsByUserId(userId));
    }

    @PatchMapping("/channels/{channelId}")
    public ResponseEntity<ChannelResponse> updateChannel(@PathVariable("channelId") UUID channelId, @RequestBody ChannelUpdateRequest request) {
        return ResponseEntity.ok().body(channelService.updateChannel(request.toServiceRequest(channelId)));
    }

    @DeleteMapping( "/channels/{channelId}")
    public ResponseEntity<Void> deleteChannel(@PathVariable("channelId") UUID channelId) {
        channelService.deleteChannel(channelId);
        return ResponseEntity.ok().build();
    }

}
