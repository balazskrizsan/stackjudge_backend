package com.kbalazsworks.stackjudge.domain.notification_module.entities;

import java.time.LocalDateTime;

public interface ITypedNotification
{
    Long getId();

    String getIdsUserId();

    short getType();

    Object getData();

    LocalDateTime getCreatedAt();

    LocalDateTime getViewedAt();
}
