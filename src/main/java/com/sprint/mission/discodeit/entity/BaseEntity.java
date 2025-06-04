package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public abstract class BaseEntity {

    protected final UUID id;
    protected final Long createdAt;
    protected Long updatedAt;

    public BaseEntity() {
        this.id = UUID.randomUUID();
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
    }

    public UUID getId() {
        return id;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public Long getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt() {
        this.updatedAt = System.currentTimeMillis();
    }

}
