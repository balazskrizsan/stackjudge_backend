package com.kbalazsworks.stackjudge.domain.services;

import com.kbalazsworks.stackjudge.api.value_objects.NotificationResponse;
import com.kbalazsworks.stackjudge.domain.entities.Notification;
import com.kbalazsworks.stackjudge.domain.repositories.NotificationRepository;
import com.kbalazsworks.stackjudge.state.entities.State;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService
{
    private final NotificationRepository notificationRepository;

    // @todo: test
    public NotificationResponse searchMyNotifications(Short limit, State state)
    {
        List<Notification> notifications = notificationRepository.searchMyNotifications(
            limit,
            state.currentUser().getId()
        );

        boolean hasNew = notifications.stream().filter(n -> null == n.getViewed()).findFirst().orElse(null) != null;

        return new NotificationResponse(notifications, hasNew);
    }
}
