package com.sprint.mission.discodeit.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ChannelErrorCode implements ErrorCode {


    CHANNEL_NOT_FOUND(404, "CHANNEL_001", "CHANNEL_NOT_FOUND");

    private final int status;
    private final String code;
    private final String message;
}

