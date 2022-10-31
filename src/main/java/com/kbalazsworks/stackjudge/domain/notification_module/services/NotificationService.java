package com.kbalazsworks.stackjudge.domain.notification_module.services;

import com.kbalazsworks.stackjudge.domain.notification_module.entities.ITypedNotification;
import com.kbalazsworks.stackjudge.domain.notification_module.services.notification_service.SearchMyNotificationsService;
import com.kbalazsworks.stackjudge.domain.notification_module.value_objects.NotificationResponse;
import com.kbalazsworks.stackjudge.state.entities.State;
import com.kbalazsworks.stackjudge.state.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService
{
    private final AccountService               accountService;
    private final SearchMyNotificationsService searchMyNotificationsService;
    private final CrudNotificationService      crudNotificationService;

    // @todo: test
    public NotificationResponse searchMyNotifications(Short limit, State state) throws Exception
    {
        List<ITypedNotification> typedNotifications = searchMyNotificationsService.convertToTypedNotification(
            crudNotificationService.searchMyNotifications(limit, state.currentIdsUser().getIdsUserId())
        );

        List<String> affectedUsersIds = searchMyNotificationsService.getUserIdsFromDataProtectedReviewType(typedNotifications);

        affectedUsersIds.add(accountService.getCurrentUser().getIdsUserId());

        return new NotificationResponse(
            typedNotifications,
            searchMyNotificationsService.hasNew(typedNotifications),
            typedNotifications.stream().filter(n -> null == n.getViewedAt()).count(),
            accountService.findByIdsWithIdMap(affectedUsersIds)
        );
    }
}
