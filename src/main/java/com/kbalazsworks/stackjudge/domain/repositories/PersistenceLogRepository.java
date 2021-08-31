package com.kbalazsworks.stackjudge.domain.repositories;

import com.kbalazsworks.stackjudge.domain.entities.TypedPersistenceLog;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.codehaus.jackson.map.ObjectMapper;
import org.jooq.JSONB;
import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository
@Log4j2
public class PersistenceLogRepository extends AbstractRepository
{
    private final com.kbalazsworks.stackjudge.db.tables.PersistenceLog persistenceLogTable
        = com.kbalazsworks.stackjudge.db.tables.PersistenceLog.PERSISTENCE_LOG;

    public <T> void create(@NonNull TypedPersistenceLog<T> typedPersistenceLog)
    {
        String data = "{}";
        try
        {
            data = new ObjectMapper().writeValueAsString(typedPersistenceLog.getData());
        }
        catch (IOException e)
        {
            log.error("Parse error on TypeNotification.data", e);
        }

        getQueryBuilder()
            .insertInto(
                persistenceLogTable,
                persistenceLogTable.TYPE,
                persistenceLogTable.DATA,
                persistenceLogTable.CREATED_AT
            )
            .values(
                typedPersistenceLog.getType().getValue(),
                JSONB.valueOf(data),
                typedPersistenceLog.getCreatedAt()
            )
            .execute();
    }
}
