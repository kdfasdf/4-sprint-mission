package com.sprint.mission.discodeit.util;

import com.sprint.mission.discodeit.entity.BaseEntity;
import java.util.Collection;
import java.util.UUID;

public class DataExistenceChecker {
    public static <T extends BaseEntity> boolean isExistDataInField(Collection<T> field, T data) {
        return field.stream()
                .anyMatch(element -> element.getId() == data.getId());
    }

}
