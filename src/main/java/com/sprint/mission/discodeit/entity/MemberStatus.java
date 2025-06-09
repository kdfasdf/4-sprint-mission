package com.sprint.mission.discodeit.entity;

import java.util.Arrays;

/**
 * 지금 진행상황에서는 statusCode를 가질 필요는 없을 것 같지만 연습용으로 구현해주었습니다
 */
public enum MemberStatus {
    ACTIVE("STATUS-001", "활성화 회원입니다"),
    DORMANT("STATUS-002", "휴면 회원입니다"),
    DELETED("STATUS-003", "탈퇴한 회원입니다");

    private String statusCode;
    private String description;

    private MemberStatus(String statusCode, String description) {
        this.statusCode = statusCode;
        this.description = description;
    }

    public String getStatusCode() {
        return this.statusCode;
    }

    public String getDescriptionByCode(String statusCode) {
        return Arrays.stream(MemberStatus.values())
                .filter(status -> status.getStatusCode().equals(statusCode))
                .findFirst()
                .map(MemberStatus::getDescription)
                .orElse(null);
    }

    private String getDescription() {
        return this.description;
    }

}
