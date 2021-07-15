package com.kbalazsworks.stackjudge.domain.services;

import com.kbalazsworks.stackjudge.domain.entities.ProtectedReviewLog;
import com.kbalazsworks.stackjudge.domain.entities.TypedNotification;
import com.kbalazsworks.stackjudge.domain.entities.notification.DataProtectedReview;
import com.kbalazsworks.stackjudge.domain.enums.notification.NotificationTypeEnum;
import com.kbalazsworks.stackjudge.domain.repositories.ProtectedReviewLogRepository;
import com.kbalazsworks.stackjudge.state.entities.State;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProtectedReviewLogService
{
    private final ProtectedReviewLogRepository protectedReviewLogRepository;
    private final CrudNotificationService      crudNotificationService;

    // @todo: test
    public void create(ProtectedReviewLog protectedReviewLog, State state)
    {
        protectedReviewLogRepository.create(protectedReviewLog);

        crudNotificationService.create(new TypedNotification<>(
            null,
            protectedReviewLog.viewerUserId(),
            NotificationTypeEnum.PROTECTED_VIEW.getValue(),
            new DataProtectedReview(
                state.currentUser().getId()
            ),
            state.now(),
            null
        ));
    }
}
