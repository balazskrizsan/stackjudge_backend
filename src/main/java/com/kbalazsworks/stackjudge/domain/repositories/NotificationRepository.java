package com.kbalazsworks.stackjudge.domain.repositories;

import com.kbalazsworks.stackjudge.domain.entities.RawNotification;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.jooq.impl.DSL.field;

@Repository
public class NotificationRepository extends AbstractRepository
{
    private final com.kbalazsworks.stackjudge.db.tables.Notification notificationTable
        = com.kbalazsworks.stackjudge.db.tables.Notification.NOTIFICATION;

    public List<RawNotification> searchMyNotifications(long limit, long userId)
    {
        return getQueryBuilder()
            .select(
                notificationTable.ID,
                notificationTable.USER_ID,
                notificationTable.TYPE,
                field("{0}", String.class, notificationTable.DATA),
                notificationTable.CREATED_AT,
                notificationTable.VIEWED_AT
            )
            .from(notificationTable)
            .where(
                notificationTable.USER_ID.eq(userId)
                    .and(notificationTable.VIEWED_AT.isNull())
            )
            .orderBy(notificationTable.ID.desc())
            .limit(limit)
            .fetchInto(RawNotification.class);
    }
}
