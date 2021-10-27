package com.kbalazsworks.stackjudge.domain.notification_module.repositories;

import com.kbalazsworks.stackjudge.domain.common_module.repositories.AbstractRepository;
import com.kbalazsworks.stackjudge.domain.notification_module.entities.RawNotification;
import com.kbalazsworks.stackjudge.domain.notification_module.entities.TypedNotification;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.ObjectMapper;
import org.jooq.JSONB;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.jooq.impl.DSL.field;

@Repository
@Slf4j
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
            .where(notificationTable.USER_ID.eq(userId))
            .orderBy(notificationTable.ID.desc())
            .limit(limit)
            .fetchInto(RawNotification.class);
    }

    public void delete(long notificationId, Long userId)
    {
        getQueryBuilder()
            .deleteFrom(notificationTable)
            .where(
                notificationTable.ID.eq(notificationId)
                    .and(notificationTable.USER_ID.eq(userId))
            )
            .execute();
    }

    public void markAsRead(long notificationId, Long userId, LocalDateTime now)
    {
        getQueryBuilder()
            .update(notificationTable)
            .set(notificationTable.VIEWED_AT, now)
            .where(
                notificationTable.ID.eq(notificationId)
                    .and(notificationTable.USER_ID.eq(userId))
            )
            .execute();
    }

    public <T> void create(TypedNotification<T> typedNotification)
    {
        String data = "{}";
        try
        {
            data = new ObjectMapper().writeValueAsString(typedNotification.getData());
        }
        catch (IOException e)
        {
            log.error("Parse error on TypeNotification.data", e);
        }

        getQueryBuilder()
            .insertInto(
                notificationTable,
                notificationTable.USER_ID,
                notificationTable.TYPE,
                notificationTable.DATA,
                notificationTable.CREATED_AT
            )
            .values(
                typedNotification.getUserId(),
                typedNotification.getType(),
                JSONB.valueOf(data),
                typedNotification.getCreatedAt()
            )
            .execute();
    }
}
