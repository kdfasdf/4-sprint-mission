package com.sprint.mission.discodeit.event;

import com.sprint.mission.discodeit.entity.Role;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RoleUpdatedEvent {
    private final UUID changedUserId;
    private final Role oldRole;
    private final Role newRole;
}
