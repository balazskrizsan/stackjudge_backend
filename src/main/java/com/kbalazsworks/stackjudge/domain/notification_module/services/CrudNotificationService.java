package com.kbalazsworks.stackjudge.domain.notification_module.services;

import com.kbalazsworks.stackjudge.domain.notification_module.entities.RawNotification;
import com.kbalazsworks.stackjudge.domain.notification_module.entities.TypedNotification;
import com.kbalazsworks.stackjudge.domain.notification_module.repositories.NotificationRepository;
import com.kbalazsworks.stackjudge.state.entities.State;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CrudNotificationService
{
    private final NotificationRepository notificationRepository;

    // @todo: test
    public void delete(long notificationId, State state)
    {
        notificationRepository.delete(notificationId, state.currentUser().getId());
    }

    // @todo: test
    public void markAsRead(long notificationId, State state)
    {
        notificationRepository.markAsRead(notificationId, state.currentUser().getId(), state.now());
    }

    public <T> void create(TypedNotification<T> rawNotification)
    {
        notificationRepository.create(rawNotification);
    }

    public List<RawNotification> searchMyNotifications(long limit, long userId)
    {
        return this.notificationRepository.searchMyNotifications(limit, userId);
    }
}
