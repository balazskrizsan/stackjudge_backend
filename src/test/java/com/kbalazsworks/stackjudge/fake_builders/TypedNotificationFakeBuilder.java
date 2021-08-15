package com.kbalazsworks.stackjudge.fake_builders;

import com.kbalazsworks.stackjudge.domain.entities.TypedNotification;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Accessors(fluent = true)
@Getter
@Setter
public class TypedNotificationFakeBuilder
{
    private Long defaultId1 = 104001L;

    private Long          id        = defaultId1;
    private long          userId    = 2L;
    private short         type      = 3;
    private Object        data      = new Object();
    private LocalDateTime createdAt = LocalDateTime.of(2011, 11, 22, 1, 2, 3);
    private LocalDateTime viewedAt  = LocalDateTime.of(2021, 11, 22, 1, 2, 3);

    public TypedNotification<Object> build()
    {
        return new TypedNotification<>(id, userId, type, data, createdAt, viewedAt);
    }
}
