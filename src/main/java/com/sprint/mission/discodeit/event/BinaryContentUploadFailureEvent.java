package com.sprint.mission.discodeit.event;

import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BinaryContentUploadFailureEvent {
    private final String requestId;
    private final UUID binaryContentId;
    private final String reason;
}
