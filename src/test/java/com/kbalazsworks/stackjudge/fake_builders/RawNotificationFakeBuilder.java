package com.kbalazsworks.stackjudge.fake_builders;

import com.kbalazsworks.stackjudge.MockFactory;
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
    public static Long defaultId1 = TypedNotificationFakeBuilder.defaultId1;

    private Long          id        = defaultId1;
    private String        userId    = IdsUserFakeBuilder.defaultId1;
    private short         type      = NotificationTypeEnum.PROTECTED_VIEW.getValue();
    private String        data      = "{" +
        "\"viewerIdsUserId\": \"" + DataProtectedReviewFakeBuilder.defaultViewerUserId + "\"" +
        "}";
    private LocalDateTime createdAt = MockFactory.testLocalDateTimeMock1;
    private LocalDateTime viewedAt  = MockFactory.testLocalDateTimeMock2;

    public RawNotification build()
    {
        return new RawNotification(id, userId, type, data, createdAt, viewedAt);
    }
}
