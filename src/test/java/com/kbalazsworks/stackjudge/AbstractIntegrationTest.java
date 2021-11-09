package com.kbalazsworks.stackjudge;

import com.kbalazsworks.stackjudge.db.tables.Address;
import com.kbalazsworks.stackjudge.db.tables.Company;
import com.kbalazsworks.stackjudge.db.tables.CompanyOwnRequest;
import com.kbalazsworks.stackjudge.db.tables.Group;
import com.kbalazsworks.stackjudge.db.tables.PersistenceLog;
import com.kbalazsworks.stackjudge.db.tables.Review;
import com.kbalazsworks.stackjudge.db.tables.Users;
import com.kbalazsworks.stackjudge.domain.common_module.services.JooqService;
import org.jooq.DSLContext;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;

public abstract class AbstractIntegrationTest extends AbstractTest
{
    protected final Company           companyTable           = Company.COMPANY;
    protected final Address           addressTable           = Address.ADDRESS;
    protected final Group             groupTable             = Group.GROUP;
    protected final Review            reviewTable            = Review.REVIEW;
    protected final Users             usersTable             = Users.USERS;
    protected final PersistenceLog    persistenceLogTable    = PersistenceLog.PERSISTENCE_LOG;
    protected final CompanyOwnRequest companyOwnRequestTable = CompanyOwnRequest.COMPANY_OWN_REQUEST;

    private RedisConnection redisTestConnection = null;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private JooqService jooqService;

    public RedisConnection getRedisConnection()
    {
        if (null == redisTestConnection)
        {
            redisTestConnection = redisConnectionFactory.getConnection();
        }

        return redisTestConnection;
    }

    protected DSLContext getQueryBuilder()
    {
        return jooqService.getDbContext();
    }
}
