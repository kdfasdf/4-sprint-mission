package com.sprint.mission.discodeit.util;

import com.sprint.mission.discodeit.entity.BaseEntity;
import java.util.Collection;

/**
 * 도메인 객체가 유틸클래스에 의존하면 강결합이 발생하여
 * 객체지향 원칙에 위배된다는 것은 알고 있습니다.
 * 다만 지금은 연습을 목적으로 작성보았습니다.
 */
public class DataExistenceChecker {
    public static <T extends BaseEntity> boolean isExistDataInField(Collection<T> field, T data) {
        return field.stream()
                .anyMatch(element -> element.getId() == data.getId());
    }
}
