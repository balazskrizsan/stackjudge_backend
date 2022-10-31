package com.kbalazsworks.stackjudge.state.repositories;

import com.kbalazsworks.stackjudge.db.tables.records.UsersRecord;
import com.kbalazsworks.stackjudge.domain.common_module.repositories.AbstractRepository;
import com.kbalazsworks.stackjudge.state.entities.User;
import com.kbalazsworks.stackjudge.state.exceptions.StateException;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.jooq.Record;
import org.springframework.stereotype.Repository;

@Repository
@Log4j2
public class UserJooqRepository extends AbstractRepository
{
    private final com.kbalazsworks.stackjudge.db.tables.Users userTable
        = com.kbalazsworks.stackjudge.db.tables.Users.USERS;

    public @NonNull User create(@NonNull User user) throws StateException
    {
        Record record = getQueryBuilder()
            .insertInto(
                userTable,
                userTable.IDS_USER_ID
            )
            .values(
                user.getIdsUserId()
            )
            .returningResult(userTable.fields())
            .fetchOne();

        if (null == record)
        {
            log.error("User creation failed: {}", user);

            throw new StateException("User creation failed.");
        }

        return record.into(User.class);
    }

    public User get(String idsUserId) throws StateException
    {
        UsersRecord user =  getQueryBuilder()
            .selectFrom(userTable)
            .where(userTable.IDS_USER_ID.eq(idsUserId))
            .fetchOne();

        if (null == user)
        {
            throw new StateException("User not found");
        }

        return user.into(User.class);
    }

    public String findPushoverUserToken(@NonNull String idsUserid) throws Exception
    {
        throw new Exception("Must be loaded from IdsService");
    }
}
