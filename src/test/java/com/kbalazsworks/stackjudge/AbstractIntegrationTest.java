package com.kbalazsworks.stackjudge;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.kbalazsworks.stackjudge.db.tables.Address;
import com.kbalazsworks.stackjudge.db.tables.Company;
import com.kbalazsworks.stackjudge.db.tables.CompanyOwnRequest;
import com.kbalazsworks.stackjudge.db.tables.Group;
import com.kbalazsworks.stackjudge.db.tables.Notification;
import com.kbalazsworks.stackjudge.db.tables.PersistenceLog;
import com.kbalazsworks.stackjudge.db.tables.ProtectedReviewLog;
import com.kbalazsworks.stackjudge.db.tables.Review;
import com.kbalazsworks.stackjudge.db.tables.Users;
import com.kbalazsworks.stackjudge.domain.common_module.services.JooqService;
import com.kbalazsworks.stackjudge.mocking.IdsWireMocker;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public abstract class AbstractIntegrationTest extends AbstractTest
{
    protected final Company            companyTable            = Company.COMPANY;
    protected final Address            addressTable            = Address.ADDRESS;
    protected final Group              groupTable              = Group.GROUP;
    protected final Review             reviewTable             = Review.REVIEW;
    protected final Users              usersTable              = Users.USERS;
    protected final PersistenceLog     persistenceLogTable     = PersistenceLog.PERSISTENCE_LOG;
    protected final ProtectedReviewLog protectedReviewLogTable = ProtectedReviewLog.PROTECTED_REVIEW_LOG;
    protected final CompanyOwnRequest  companyOwnRequestTable  = CompanyOwnRequest.COMPANY_OWN_REQUEST;
    protected final Notification       notificationTable       = Notification.NOTIFICATION;

    private static RedisConnection redisTestConnection = null;

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

    protected WireMockServer createStartAndGetIdsMockServer()
    {
        WireMockServer wireMockServer = new WireMockServer(
            wireMockConfig()
                .httpsPort(5001)
                .httpDisabled(true)
                .keystorePath("W:\\Java\\stackjudge\\src\\main\\resources\\keystore\\dev-keystore.p12")
                .keystorePassword("password")
        );
        wireMockServer.start();
        configureFor("localhost", 5001);

        IdsWireMocker.mockGetWellKnownOpenidConfiguration(wireMockServer);

        return wireMockServer;
    }

    protected WireMockServer createStartAndGetAwsMockServer()
    {
        WireMockServer wireMockServer = new WireMockServer(
            wireMockConfig()
                .httpsPort(83)
                .httpDisabled(true)
                .keystorePath("W:\\Java\\stackjudge\\src\\main\\resources\\keystore\\dev-keystore.p12")
                .keystorePassword("password")
        );
        wireMockServer.start();
        configureFor("localhost", 83);

        IdsWireMocker.mockGetWellKnownOpenidConfiguration(wireMockServer);

        return wireMockServer;
    }
}
