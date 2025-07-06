package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.channel.ChannelResponse;
import com.sprint.mission.discodeit.dto.channel.request.ChannelCreateServiceRequest;
import com.sprint.mission.discodeit.dto.channel.request.ChannelUpdateServiceRequest;
import com.sprint.mission.discodeit.dto.channel.request.PrivateChannelCreateServiceRequest;
import java.util.List;
import java.util.UUID;

public interface ChannelService {

    // 생성
    ChannelResponse createPublicChannel(ChannelCreateServiceRequest request);

    ChannelResponse createPrivateChannel(PrivateChannelCreateServiceRequest request);

    // 조회
    ChannelResponse findChannelById(UUID channelId);
    List<ChannelResponse> findAllChannelsByUserId(UUID userId);

    // 수정
    ChannelResponse updateChannel(ChannelUpdateServiceRequest request);

    // 삭제
    void deleteChannel(UUID channelId);
}
