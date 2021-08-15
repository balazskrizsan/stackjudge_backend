package com.kbalazsworks.stackjudge.domain.services.notification_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbalazsworks.stackjudge.domain.entities.ITypedNotification;
import com.kbalazsworks.stackjudge.domain.entities.RawNotification;
import com.kbalazsworks.stackjudge.domain.entities.TypedNotification;
import com.kbalazsworks.stackjudge.domain.entities.notification.DataProtectedReview;
import com.kbalazsworks.stackjudge.domain.enums.notification.NotificationTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SearchMyNotificationsService
{
    // @todo: test
    public List<ITypedNotification> convertToTypedNotification(List<RawNotification> rawNotifications)
    {
        return rawNotifications
            .stream()
            .map(r -> {
                if (r.getType() == NotificationTypeEnum.PROTECTED_VIEW.getValue())
                {
                    return new TypedNotification<>(
                        r.getId(),
                        r.getUserId(),
                        r.getType(),
                        getDataProtectedReview(r.getData()),
                        r.getCreatedAt(),
                        r.getViewedAt()
                    );
                }

                return new TypedNotification<>(
                    r.getId(),
                    r.getUserId(),
                    r.getType(),
                    r.getData(),
                    r.getCreatedAt(),
                    r.getViewedAt()
                );
            })
            .collect(Collectors.toList());
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

    // @todo: test
    public List<Long> getTypedNotifications(List<ITypedNotification> typedNotifications)
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
