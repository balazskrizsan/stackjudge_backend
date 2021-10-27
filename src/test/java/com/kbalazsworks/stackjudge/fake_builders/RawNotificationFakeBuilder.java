package com.kbalazsworks.stackjudge.fake_builders;

import com.kbalazsworks.stackjudge.domain.notification_module.entities.RawNotification;
import com.kbalazsworks.stackjudge.domain.notification_module.enums.NotificationTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Accessors(fluent = true)
@Getter
@Setter
public class RawNotificationFakeBuilder
{
    private Long defaultId1 = TypedNotificationFakeBuilder.defaultId1;

    private Long          id        = defaultId1;
    private long          userId    = 2;
    private short         type      = NotificationTypeEnum.PROTECTED_VIEW.getValue();
    private String        data      = "{\"viewerUserId\": " + DataProtectedReviewFakeBuilder.defaultViewerUserId + "}";
    private LocalDateTime createdAt = LocalDateTime.of(2011, 11, 22, 1, 2, 3);
    private LocalDateTime viewedAt  = LocalDateTime.of(2021, 11, 22, 1, 2, 3);

    public RawNotification build()
    {
        return new RawNotification(id, userId, type, data, createdAt, viewedAt);
    }
}
