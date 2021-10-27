package com.kbalazsworks.stackjudge.domain.notification_module.services.notification_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbalazsworks.stackjudge.domain.notification_module.entities.ITypedNotification;
import com.kbalazsworks.stackjudge.domain.notification_module.entities.RawNotification;
import com.kbalazsworks.stackjudge.domain.notification_module.entities.TypedNotification;
import com.kbalazsworks.stackjudge.domain.review_module.entities.DataProtectedReview;
import com.kbalazsworks.stackjudge.domain.notification_module.enums.NotificationTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SearchMyNotificationsService
{
    public List<ITypedNotification> convertToTypedNotification(List<RawNotification> rawNotifications)
    {
        return rawNotifications.stream().map(this::dataMapper).collect(Collectors.toList());
    }

    private TypedNotification<Object> dataMapper(RawNotification notification)
    {
        Object data = notification.getData();
        if (notification.getType() == NotificationTypeEnum.PROTECTED_VIEW.getValue())
        {
            data = getDataProtectedReview(notification.getData());
        }

        return new TypedNotification<>(
            notification.getId(),
            notification.getUserId(),
            notification.getType(),
            data,
            notification.getCreatedAt(),
            notification.getViewedAt()
        );
    }

    private DataProtectedReview getDataProtectedReview(String data)
    {
        try
        {
            return new ObjectMapper().readValue(data, DataProtectedReview.class);
        }
        catch (Exception e)
        {
            log.error("Notification data parse error: " + data);

            return new DataProtectedReview(0L);
        }
    }

    public boolean hasNew(List<ITypedNotification> typedNotifications)
    {
        return null != typedNotifications
            .stream()
            .filter(n -> null == n.getViewedAt())
            .findFirst()
            .orElse(null);
    }

    public List<Long> getUserIdsFromDataProtectedReviewType(List<ITypedNotification> typedNotifications)
    {
        return typedNotifications
            .stream()
            .map(r -> {
                if (r.getType() == NotificationTypeEnum.PROTECTED_VIEW.getValue())
                {
                    return ((DataProtectedReview) r.getData()).getViewerUserId();
                }

                return 0L;
            })
            .distinct()
            .filter(r -> r != 0)
            .collect(Collectors.toList());
    }
}
