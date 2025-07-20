package com.sprint.mission.discodeit.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatusErrorCode implements ErrorCode {

    USER_STATUS_NOT_FOUND(404, "USER_STATUS_001", "USER_STATUS_NOT_FOUND"),
    USER_STATUS_ALREADY_EXIST(400, "USER_STATUS_002", "USER_STATUS_ALREADY_EXIST");

    private final int status;
    private final String code;
    private final String message;
}
