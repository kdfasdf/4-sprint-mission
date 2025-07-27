package com.sprint.mission.discodeit.dto.channel.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChannelCreateRequest {

    @NotBlank(message = "채널 이름은 null이거나 공백이면 안됨")
    private final String name;

    @NotBlank(message = "채널 설명은 null이면 안됨")
    private final String description;

    public ChannelCreateServiceRequest toServiceRequest() {
        return ChannelCreateServiceRequest.builder()
                .name(name)
                .description(description)
                .build();
    }
}
