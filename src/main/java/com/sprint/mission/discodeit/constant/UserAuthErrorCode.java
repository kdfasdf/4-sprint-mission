package com.sprint.mission.discodeit.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserAuthErrorCode implements ErrorCode {

    INVALID_USERNAME(401, "AUTH_002", "INVALID_USERNAME"),
    INVALID_PASSWORD(401, "AUTH_002", "INVALID_PASSWORD"),
    AUTHENTICATION_FAILED(401, "AUTH_003", "AUTHENTICATION_FAILED");

    private final int status;
    private final String code;
    private final String message;

}
