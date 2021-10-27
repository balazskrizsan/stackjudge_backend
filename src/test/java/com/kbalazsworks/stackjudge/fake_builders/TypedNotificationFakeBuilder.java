package com.kbalazsworks.stackjudge.fake_builders;

import com.kbalazsworks.stackjudge.domain.notification_module.entities.TypedNotification;
import com.kbalazsworks.stackjudge.domain.notification_module.enums.NotificationTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Accessors(fluent = true)
@Getter
@Setter
public class TypedNotificationFakeBuilder<T>
{
    public static Long defaultId1 = 104001L;

    private Long          id        = defaultId1;
    private long          userId    = 2L;
    private short         type      = NotificationTypeEnum.PROTECTED_VIEW.getValue();
    private T             data;
    private LocalDateTime createdAt = LocalDateTime.of(2011, 11, 22, 1, 2, 3);
    private LocalDateTime viewedAt  = LocalDateTime.of(2021, 11, 22, 1, 2, 3);

    public TypedNotification<T> build()
    {
        return new TypedNotification<>(id, userId, type, data, createdAt, viewedAt);
    }
}
