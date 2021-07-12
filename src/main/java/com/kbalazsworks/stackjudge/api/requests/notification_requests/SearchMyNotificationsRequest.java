package com.kbalazsworks.stackjudge.api.requests.notification_requests;

import com.kbalazsworks.stackjudge.api.enums.NotificationSearchLimitEnum;

import java.util.List;

public record SearchMyNotificationsRequest(Short limit, List<Short> requestRelationIds, Short navigationId)
{
    public Short limit()
    {
        if (NotificationSearchLimitEnum.getByValue(limit) == null)
        {
            return NotificationSearchLimitEnum.DEFAULT.getValue();
        }

        return limit;
    }
}
