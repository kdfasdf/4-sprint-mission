package com.sprint.mission.discodeit.entity;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ChannelType {

    PUBLIC_CHATTING("CHANNEL-100", "공공 채팅방"),
    PUBLIC_VOICE("CHANNEL-101", "공공 음성채팅방"),

    PRIVATE_CHATTING("CHANNEL-200", "비밀 채팅방"),
    PRIVATE_VOICE("CHANNEL-201", "비밀 음성채팅방");

    private final String code;
    private final String description;

    public static ChannelType getChannelTypeByCode(String code) {
        return Arrays.stream(ChannelType.values())
                .filter(channelType -> channelType.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("ChannelType not found."));
    }

    public String getDescriptionByCode(String code) {
        return Arrays.stream(ChannelType.values())
                .filter(channelType -> channelType.getCode().equals(code))
                .findFirst()
                .map(ChannelType::getDescription)
                .orElseThrow(() -> new IllegalArgumentException("ChannelType not found."));
    }
}
