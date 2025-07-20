package com.sprint.mission.discodeit.exception;

import com.sprint.mission.discodeit.constant.ErrorCode;

public class UserStatusException extends BusinessException {
    public UserStatusException(ErrorCode errorCode) {
        super(errorCode);
    }
}
