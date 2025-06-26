package com.sprint.mission.discodeit.dto.readstatus.request;

import com.sprint.mission.discodeit.entity.ReadStatus;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReadStatusCreateServiceRequest {

    private UUID channelId;
    private UUID userId;

    public ReadStatus toEntity() {
        return ReadStatus.builder()
                .channelId(channelId)
                .userId(userId)
                .build();
    }

}
