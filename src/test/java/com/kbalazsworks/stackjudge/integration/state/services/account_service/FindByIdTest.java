package com.kbalazsworks.stackjudge.integration.state.services.account_service;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.fake_builders.IdsUserFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.UserFakeBuilder;
import com.kbalazsworks.stackjudge.mocking.IdsWireMocker;
import com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.ids._entities.IdsUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

public class FindByIdTest extends AbstractIntegrationTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    private WireMockServer wireMockServer;

    @Before
    public void before()
    {
        wireMockServer = createStartAndGetIdsMockServer();
    }

    @After
    public void after()
    {
        wireMockServer.stop();
    }

    @Test
    @SqlGroup(
        {
            @Sql(
                executionPhase = BEFORE_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {
                    "classpath:test/sqls/_truncate_tables.sql",
                    "classpath:test/sqls/preset_add_1_user.sql",
                }
            ),
            @Sql(
                executionPhase = AFTER_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql"}
            )
        }
    )
    public void selectingFromFilledDb_returnsUserAndCallLogger()
    {
        // Arrange
        String  testedUserId    = UserFakeBuilder.defaultId1;
        IdsUser expectedIdsUser = new IdsUserFakeBuilder().build();
        IdsWireMocker.mockGetApiAccountList(wireMockServer);
        IdsWireMocker.mockPostConnectToken(wireMockServer);

        // Act
        IdsUser actualIdsUser = serviceFactory.getAccountService().findById(testedUserId);

        // Assert
        assertThat(actualIdsUser).usingRecursiveComparison().isEqualTo(expectedIdsUser);
    }
}
