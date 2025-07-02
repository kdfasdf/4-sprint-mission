package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.binarycontent.BinaryContentResponse;
import com.sprint.mission.discodeit.service.BinaryContentService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BinaryContentController {

    private final BinaryContentService binaryContentService;

//    @RequestMapping(method = RequestMethod.GET, value = "/binarycontents")
//    public ResponseEntity<List<BinaryContentResponse>> getBinaryContents(@RequestBody List<UUID> filesId) {
//        return ResponseEntity.ok(binaryContentService.findAllByIdIn(filesId));
//    }

    @RequestMapping(method = RequestMethod.GET, value = "/binarycontents/{profileId}")
    public ResponseEntity<BinaryContentResponse> getBinaryContent(@PathVariable("profileId") UUID binaryContentId) {
        return ResponseEntity.ok(binaryContentService.findById(binaryContentId));
    }
}
