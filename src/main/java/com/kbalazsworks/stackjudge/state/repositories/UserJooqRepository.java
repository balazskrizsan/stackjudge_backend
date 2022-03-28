package com.kbalazsworks.stackjudge.state.repositories;

import com.kbalazsworks.stackjudge.domain.common_module.repositories.AbstractRepository;
import com.kbalazsworks.stackjudge.state.entities.User;
import com.kbalazsworks.stackjudge.state.exceptions.StateException;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.jooq.Record;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
@Log4j2
public class UserJooqRepository extends AbstractRepository
{
    private final com.kbalazsworks.stackjudge.db.tables.Users userTable
        = com.kbalazsworks.stackjudge.db.tables.Users.USERS;

    public void updateFacebookAccessToken(@NonNull String token, @NonNull Long facebookUserId)
    {
        getQueryBuilder()
            .update(userTable)
            .set(userTable.FACEBOOK_ACCESS_TOKEN, token)
            .where(userTable.FACEBOOK_ID.eq(BigDecimal.valueOf(facebookUserId)))
            .execute();
    }

    public @NonNull User create(@NonNull User user) throws Exception
    {
        Record record = getQueryBuilder()
            .insertInto(
                userTable,
                userTable.IS_EMAIL_USER,
                userTable.IS_FACEBOOK_USER,
                userTable.PROFILE_PICTURE_URL,
                userTable.USERNAME,
                userTable.PASSWORD,
                userTable.FACEBOOK_ACCESS_TOKEN,
                userTable.FACEBOOK_ID
            )
            .values(
                user.getIsEmailUser(),
                user.getIsFacebookUser(),
                user.getProfilePictureUrl(),
                user.getUsername(),
                user.getPassword(),
                user.getFacebookAccessToken(),
                BigDecimal.valueOf(user.getFacebookId())
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
}
