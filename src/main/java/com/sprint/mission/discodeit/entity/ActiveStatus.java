package com.sprint.mission.discodeit.entity;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActiveStatus {
    ACTIVE("STATUS-001", "활성화 회원입니다"),
    DORMANT("STATUS-002", "휴면 회원입니다"),
    DELETED("STATUS-003", "탈퇴한 회원입니다");

    private final String statusCode;
    private final String description;


    public String getStatusCode() {
        return this.statusCode;
    }

    public String getDescriptionByCode(String statusCode) {
        return Arrays.stream(ActiveStatus.values())
                .filter(status -> status.getStatusCode().equals(statusCode))
                .findFirst()
                .map(ActiveStatus::getDescription)
                .orElse(null);
    }
}
