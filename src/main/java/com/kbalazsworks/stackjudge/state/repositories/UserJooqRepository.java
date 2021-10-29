package com.kbalazsworks.stackjudge.state.repositories;

import com.kbalazsworks.stackjudge.domain.common_module.repositories.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class UserJooqRepository extends AbstractRepository
{
    private final com.kbalazsworks.stackjudge.db.tables.Users userTable
        = com.kbalazsworks.stackjudge.db.tables.Users.USERS;

    public void updateFacebookAccessToken(String token, Long facebookUserId)
    {
        getQueryBuilder()
            .update(userTable)
            .set(userTable.FACEBOOK_ACCESS_TOKEN, token)
            .where(userTable.FACEBOOK_ID.eq(BigDecimal.valueOf(facebookUserId)))
            .execute();
    }
}
