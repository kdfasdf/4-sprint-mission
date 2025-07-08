package com.sprint.mission.discodeit.dto.readstatus.request;

import java.util.UUID;
import lombok.Getter;


/**
 * 추후 도메인 객체에 업데이트 할만한 필드 추가되면 같이 추가할 것
 */
@Getter
public class ReadStatusUpdateRequest {

    private final UUID readStatusId;

    private final UUID userId;

    private final UUID channelId;

    public ReadStatusUpdateRequest(UUID readStatusId, UUID userId, UUID channelId) {
        validate(readStatusId, userId, channelId);
        this.readStatusId = readStatusId;
        this.userId = userId;
        this.channelId = channelId;
    }

    public ReadStatusUpdateServiceRequest toServiceRequest() {
        return ReadStatusUpdateServiceRequest.builder()
                .readStatusId(readStatusId)
                .userId(userId)
                .channelId(channelId)
                .build();
    }

    public void validate(UUID readStatusId, UUID userId, UUID channelId) {
        if(readStatusId == null) {
            throw new IllegalArgumentException("readStatusId는 null이면 안됨");
        }

        if(userId == null && channelId == null) {
            throw new IllegalArgumentException("userId와 channelId는 모두 null이면 안됨");
        }

        if (userId == null) {
            throw new IllegalArgumentException("userId는 null이면 안됨");
        }

        if (channelId == null) {
            throw new IllegalArgumentException("channelId는 null이면 안됨");
        }
    }
}
