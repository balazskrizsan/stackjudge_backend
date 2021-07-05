package com.kbalazsworks.stackjudge.domain.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbalazsworks.stackjudge.domain.entities.ITypedNotification;
import com.kbalazsworks.stackjudge.domain.entities.RawNotification;
import com.kbalazsworks.stackjudge.domain.entities.TypedNotification;
import com.kbalazsworks.stackjudge.domain.entities.notification.DataProtectedReview;
import com.kbalazsworks.stackjudge.domain.enums.notification.NotificationType;
import com.kbalazsworks.stackjudge.domain.repositories.NotificationRepository;
import com.kbalazsworks.stackjudge.domain.value_objects.NotificationResponse;
import com.kbalazsworks.stackjudge.state.entities.State;
import com.kbalazsworks.stackjudge.state.entities.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService
{
    private final NotificationRepository notificationRepository;

    // @todo: test
    public NotificationResponse searchMyNotifications(Short limit, State state)
    {
        List<ITypedNotification> typedNotifications = convertToTypedNotification(
            notificationRepository.searchMyNotifications(limit, state.currentUser().getId())
        );

        boolean hasNew = notifications.stream().filter(n -> null == n.getViewed()).findFirst().orElse(null) != null;

        boolean hasNew = typedNotifications.stream().filter(n -> null == n.getViewedAt()).findFirst().orElse(null) != null;
        long newCount = typedNotifications.stream().filter(n -> null == n.getViewedAt()).count();

        return new NotificationResponse(typedNotifications, hasNew, newCount);
    }

    private List<ITypedNotification> convertToTypedNotification(List<RawNotification> rawNotifications)
    {
        return rawNotifications
            .stream()
            .map(r -> {
                if (r.getType() == NotificationType.PROTECTED_VIEW.getValue())
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
}
