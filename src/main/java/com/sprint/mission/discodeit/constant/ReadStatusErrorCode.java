package com.sprint.mission.discodeit.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReadStatusErrorCode implements ErrorCode {

    READ_STATUS_NOT_FOUND(404, "READ_STATUS_001", "READ_STATUS_NOT_FOUND");

    private final int status;
    private final String code;
    private final String message;
}
