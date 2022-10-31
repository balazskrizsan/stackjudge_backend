package com.kbalazsworks.stackjudge.domain.review_module.services;

import com.kbalazsworks.stackjudge.domain.persistance_log_module.entities.ProtectedReviewLog;
import com.kbalazsworks.stackjudge.domain.notification_module.entities.TypedNotification;
import com.kbalazsworks.stackjudge.domain.review_module.entities.DataProtectedReview;
import com.kbalazsworks.stackjudge.domain.notification_module.enums.NotificationTypeEnum;
import com.kbalazsworks.stackjudge.domain.notification_module.services.CrudNotificationService;
import com.kbalazsworks.stackjudge.domain.review_module.repositories.ProtectedReviewLogRepository;
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
            protectedReviewLog.viewerIdsUserId(),
            NotificationTypeEnum.PROTECTED_VIEW.getValue(),
            new DataProtectedReview(state.currentIdsUser().getIdsUserId()),
            state.now(),
            null
        ));
    }
}
