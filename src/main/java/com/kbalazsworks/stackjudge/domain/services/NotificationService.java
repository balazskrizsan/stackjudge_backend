package com.kbalazsworks.stackjudge.domain.services;

import com.kbalazsworks.stackjudge.domain.entities.ITypedNotification;
import com.kbalazsworks.stackjudge.domain.repositories.NotificationRepository;
import com.kbalazsworks.stackjudge.domain.services.notification_service.SearchMyNotificationsService;
import com.kbalazsworks.stackjudge.domain.value_objects.NotificationResponse;
import com.kbalazsworks.stackjudge.state.entities.State;
import com.kbalazsworks.stackjudge.state.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService
{
    private final NotificationRepository       notificationRepository;
    private final AccountService               accountService;
    private final SearchMyNotificationsService searchMyNotificationsService;

    // @todo: test
    public NotificationResponse searchMyNotifications(Short limit, State state)
    {
        List<ITypedNotification> typedNotifications = searchMyNotificationsService.convertToTypedNotification(
            notificationRepository.searchMyNotifications(limit, state.currentUser().getId())
        );

        List<Long> affectedUsersIds = searchMyNotificationsService.getTypedNotifications(typedNotifications);

        affectedUsersIds.add(accountService.getCurrentUser().getId());

        return new NotificationResponse(
            typedNotifications,
            searchMyNotificationsService.hasNew(typedNotifications),
            typedNotifications.stream().filter(n -> null == n.getViewedAt()).count(),
            accountService.findByUserIdsWithIdMap(affectedUsersIds)
        );
    }

    // @todo: test
    public void delete(long notificationId, State state)
    {
         notificationRepository.delete(notificationId, state.currentUser().getId());
    }
}
