package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.ApiResponse;
import com.sprint.mission.discodeit.dto.readstatus.ReadStatusResponse;
import com.sprint.mission.discodeit.dto.readstatus.request.ReadStatusCreateRequest;
import com.sprint.mission.discodeit.dto.readstatus.request.ReadStatusUpdateRequest;
import com.sprint.mission.discodeit.service.ReadStatusService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
public class ReadStatusController {

    private final ReadStatusService readStatusService;

    @PostMapping("/readStatuses")
    public ResponseEntity<ApiResponse<ReadStatusResponse>> createReadStatuses(@RequestBody ReadStatusCreateRequest reqeust) {
        return ResponseEntity.ok().body(ApiResponse.onSuccess(readStatusService.createReadStatus(reqeust.toServiceRequest())));
    }

    @PatchMapping("/readStatuses/{readStatusId}")
    public ResponseEntity<ApiResponse<ReadStatusResponse>> updateChannelReadStatus(@PathVariable("readStatusId") UUID readStatusId, @RequestBody ReadStatusUpdateRequest reqeust) {
        return ResponseEntity.ok().body(ApiResponse.onSuccess(readStatusService.updateReadStatus(reqeust.toServiceRequest(readStatusId))));
    }

    @GetMapping("/users/{userId}/readStatuses")
    public ResponseEntity<ApiResponse<List<ReadStatusResponse>>> getUserReadStatuses(@PathVariable("userId") UUID userId) {
        return ResponseEntity.ok().body(ApiResponse.onSuccess(readStatusService.findAllByUserId(userId)));
    }
}
