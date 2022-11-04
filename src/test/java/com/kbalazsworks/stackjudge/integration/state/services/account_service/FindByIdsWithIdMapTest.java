package com.kbalazsworks.stackjudge.integration.state.services.account_service;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.fake_builders.IdsUserFakeBuilder;
import com.kbalazsworks.stackjudge.mocking.IdsWireMocker;
import com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.ids._entities.IdsUser;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;

public class FindByIdsWithIdMapTest extends AbstractIntegrationTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    @SqlGroup(
        {
            @Sql(
                executionPhase = BEFORE_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {
                    "classpath:test/sqls/_truncate_tables.sql",
                    "classpath:test/sqls/preset_add_2_user.sql",
                }
            ),
            @Sql(
                executionPhase = AFTER_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql"}
            )
        }
    )
    @Test
    public void selectingFromFilledDb_returnsUses()
    {
        // Arrange
        WireMockServer wireMockServer = createStartAndGetIdsMockServer();
        IdsWireMocker.mockGetApiAccountList(wireMockServer);

        List<String> testedUserIds         = List.of(IdsUserFakeBuilder.defaultId1);
        Map<String, IdsUser> expectedUsers = Map.of(IdsUserFakeBuilder.defaultId1, new IdsUserFakeBuilder().build());

        // Act
        Map<String, IdsUser> actualUser = serviceFactory.getAccountService().findByIdsWithIdMap(testedUserIds);

        // Assert
        wireMockServer.stop();
        assertThat(actualUser).usingRecursiveComparison().isEqualTo(expectedUsers);
    }
}
