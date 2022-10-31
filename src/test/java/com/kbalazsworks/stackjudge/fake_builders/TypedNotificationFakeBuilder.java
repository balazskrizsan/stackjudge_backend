package com.kbalazsworks.stackjudge.fake_builders;

import com.kbalazsworks.stackjudge.MockFactory;
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
    private String          userId    = IdsUserFakeBuilder.defaultId1;
    private short         type      = NotificationTypeEnum.PROTECTED_VIEW.getValue();
    private T             data      = (T) new DataProtectedReviewFakeBuilder().build();
    private LocalDateTime createdAt = MockFactory.testLocalDateTimeMock1;
    private LocalDateTime viewedAt  = MockFactory.testLocalDateTimeMock2;
    ;

    public TypedNotification<T> build()
    {
        return new TypedNotification<>(id, userId, type, data, createdAt, viewedAt);
    }
}
