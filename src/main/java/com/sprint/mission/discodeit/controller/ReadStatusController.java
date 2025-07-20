package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.controller.api.ReadStatusApi;
import com.sprint.mission.discodeit.dto.readstatus.ReadStatusResponse;
import com.sprint.mission.discodeit.dto.readstatus.request.ReadStatusCreateRequest;
import com.sprint.mission.discodeit.dto.readstatus.request.ReadStatusUpdateRequest;
import com.sprint.mission.discodeit.service.ReadStatusService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/readStatuses")
public class ReadStatusController implements ReadStatusApi {

    private final ReadStatusService readStatusService;

    @Override
    @PostMapping
    public ResponseEntity<ReadStatusResponse> createReadStatus(@RequestBody ReadStatusCreateRequest reqeust) {
        return ResponseEntity.status(HttpStatus.CREATED).body(readStatusService.createReadStatus(reqeust.toServiceRequest()));
    }

    @Override
    @PatchMapping("/{readStatusId}")
    public ResponseEntity<ReadStatusResponse> updateReadStatus(@PathVariable("readStatusId") UUID readStatusId, @RequestBody ReadStatusUpdateRequest reqeust) {
        return ResponseEntity.ok().body(readStatusService.updateReadStatus(reqeust.toServiceRequest(readStatusId)));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<ReadStatusResponse>> findAllReadStatusByUserId(@RequestParam("userId") UUID userId) {
        return ResponseEntity.ok().body(readStatusService.findAllByUserId(userId));
    }
}
