package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentResponse;
import com.sprint.mission.discodeit.service.BinaryContentService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/binaryContents")
public class BinaryContentController {

    private final BinaryContentService binaryContentService;

    @GetMapping
    public ResponseEntity<List<BinaryContentResponse>> getBinaryContents(@RequestParam("binaryContentId") List<UUID> binaryContentsIds) {
        return ResponseEntity.ok().body(binaryContentService.findAllByIdIn(binaryContentsIds));
    }

    @GetMapping( "{binaryContentId}")
    public ResponseEntity<BinaryContentResponse> getBinaryContent(@PathVariable("binaryContentId") UUID binaryContentId) {
        return ResponseEntity.ok().body(binaryContentService.findById(binaryContentId));
    }
}
