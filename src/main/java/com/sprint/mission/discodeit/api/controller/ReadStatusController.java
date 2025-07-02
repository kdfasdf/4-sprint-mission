package com.sprint.mission.discodeit.api.controller;

import com.sprint.mission.discodeit.dto.readstatus.ReadStatusResponse;
import com.sprint.mission.discodeit.dto.readstatus.request.ReadStatusCreateRequest;
import com.sprint.mission.discodeit.dto.readstatus.request.ReadStatusUpdateRequest;
import com.sprint.mission.discodeit.service.ReadStatusService;
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
public class ReadStatusController {

    private final ReadStatusService readStatusService;

    @RequestMapping(method = RequestMethod.POST, value = "/readStatuses")
    public ResponseEntity<Void> createReadStatuses(@RequestBody ReadStatusCreateRequest reqeust) {
        readStatusService.createReadStatus(reqeust.toServiceRequest());
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/readStatuses/{readStatusId}")
    public ResponseEntity<ReadStatusResponse> updateChannelReadStatus(@PathVariable UUID readStatusId, @RequestBody ReadStatusUpdateRequest reqeust) {
        return ResponseEntity.ok(readStatusService.updateReadStatus(reqeust.toServiceRequest(readStatusId)));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/readStatuses/users/{userId}")
    public ResponseEntity<List<ReadStatusResponse>> getUserReadStatuses(@PathVariable UUID userId) {
        return ResponseEntity.ok(readStatusService.findAllByUserId(userId));
    }
}
