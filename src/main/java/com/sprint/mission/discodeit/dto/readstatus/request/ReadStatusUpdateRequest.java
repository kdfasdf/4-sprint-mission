package com.sprint.mission.discodeit.dto.readstatus.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * 추후 도메인 객체에 업데이트 할만한 필드 추가되면 같이 추가할 것
 */
@Getter
@AllArgsConstructor
public class ReadStatusUpdateRequest {

    @NotNull(message = "유저 Id는 null이면 안됨")
    private final UUID userId;

    @NotNull(message = "채널 Id는 null이면 안됨")
    private final UUID channelId;

    public ReadStatusUpdateServiceRequest toServiceRequest(UUID readStatusId) {
        return ReadStatusUpdateServiceRequest.builder()
                .readStatusId(readStatusId)
                .userId(userId)
                .channelId(channelId)
                .build();
    }
}
