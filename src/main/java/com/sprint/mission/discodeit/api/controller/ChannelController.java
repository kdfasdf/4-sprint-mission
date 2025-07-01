package com.sprint.mission.discodeit.api.controller;

import com.sprint.mission.discodeit.dto.channel.ChannelResponse;
import com.sprint.mission.discodeit.dto.channel.PrivateChannelResponse;
import com.sprint.mission.discodeit.dto.channel.PublicChannelResponse;
import com.sprint.mission.discodeit.dto.channel.request.ChannelCreateServiceRequest;
import com.sprint.mission.discodeit.dto.channel.request.ChannelUpdateRequest;
import com.sprint.mission.discodeit.dto.channel.request.PrivateChannelCreateRequest;
import com.sprint.mission.discodeit.service.ChannelService;
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
public class ChannelController {

    private final ChannelService channelService;

    @RequestMapping(method = RequestMethod.POST, value = "/channels/public")
    public ResponseEntity<PublicChannelResponse> createPublicChannel(@RequestBody ChannelCreateServiceRequest request) {
        channelService.createPublicChannel(request);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/channels/private")
    public ResponseEntity<PrivateChannelResponse> createPrivateChannel(@RequestBody PrivateChannelCreateRequest request) {
        channelService.createPrivateChannel(request.toServiceRequest());
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/channels/{channelId}")
    public ResponseEntity<ChannelResponse> findChannel(@PathVariable UUID channelId) {
        return ResponseEntity.ok(channelService.findChannelById(channelId));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/channels/{userId}")
    public ResponseEntity<List<ChannelResponse>> findChannels(@PathVariable UUID userId) {
        return ResponseEntity.ok(channelService.findAllChannelsByUserId(userId));
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/channels")
    public ResponseEntity<ChannelResponse> updateChannel(@PathVariable UUID channelId, @RequestBody ChannelUpdateRequest request) {
        return ResponseEntity.ok(channelService.updateChannel(request.toServiceRequest(channelId)));
    }

}
