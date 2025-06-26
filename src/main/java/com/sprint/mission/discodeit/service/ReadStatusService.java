package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.readstatus.ReadStatusResponse;
import com.sprint.mission.discodeit.dto.readstatus.request.ReadStatusCreateServiceRequest;
import com.sprint.mission.discodeit.dto.readstatus.request.ReadStatusUpdateServiceRequest;
import java.util.List;
import java.util.UUID;

public interface ReadStatusService {
    void createReadStatus(ReadStatusCreateServiceRequest request);

    ReadStatusResponse findReadStatusById(UUID readStatusId);

    ReadStatusResponse findReadStatusByUserIdAndChannelId(UUID userId, UUID channelId);

    List<ReadStatusResponse> findAllByUserId(UUID userId);

    ReadStatusResponse updateReadStatus(ReadStatusUpdateServiceRequest request);

    void deleteReadStatus(UUID readStatusId);

}
