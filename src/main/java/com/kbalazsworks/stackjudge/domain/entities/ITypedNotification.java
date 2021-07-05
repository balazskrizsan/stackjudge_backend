package com.kbalazsworks.stackjudge.domain.entities;

import java.time.LocalDateTime;

public interface ITypedNotification
{
    public Long getId();

    public long getUserId();

    public short getType();

    public Object getData();

    public LocalDateTime getCreatedAt();

    public LocalDateTime getViewedAt();
}
