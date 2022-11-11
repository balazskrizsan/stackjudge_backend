package com.kbalazsworks.stackjudge.e2e.api.controllers.company_controller;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.kbalazsworks.stackjudge.AbstractE2eTest;
import com.kbalazsworks.stackjudge.fake_builders.AddressFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.CompanyFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.GroupFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.ReviewFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.IdsUserFakeBuilder;
import com.kbalazsworks.stackjudge.mocking.AwsWireMocker;
import com.kbalazsworks.stackjudge.mocking.IdsWireMocker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GetActionTest extends AbstractE2eTest
{
    private WireMockServer idsWireMockServer;
    private WireMockServer awsWireMockServer;

    @Before
    public void before()
    {
        idsWireMockServer = createStartAndGetIdsMockServer();
        IdsWireMocker.mockGetApiAccountList(idsWireMockServer);
        IdsWireMocker.mockPostConnectToken(idsWireMockServer);

        awsWireMockServer = createStartAndGetAwsMockServer();
        AwsWireMocker.postS3Upload(awsWireMockServer);
    }

    @After
    public void after()
    {
        idsWireMockServer.stop();
        awsWireMockServer.stop();
    }

    @Test
    @SqlGroup({
        @Sql(executionPhase = BEFORE_TEST_METHOD, config = @SqlConfig(transactionMode = ISOLATED), scripts = {
            "classpath:test/sqls/_truncate_tables.sql",
            "classpath:test/sqls/preset_add_1_company.sql",
            "classpath:test/sqls/preset_add_1_address.sql",
            "classpath:test/sqls/preset_add_1_group.sql",
            "classpath:test/sqls/preset_add_1_review.sql",
            "classpath:test/sqls/preset_add_1_user.sql",
            "classpath:test/sqls/preset_add_1_company_owner.sql",
        }),
        @Sql(executionPhase = AFTER_TEST_METHOD,
            config = @SqlConfig(transactionMode = ISOLATED),
            scripts = {"classpath:test/sqls/_truncate_tables.sql"})
    }) public void callMethodWithCorrectDbData_allReturnedFieldHasValues() throws Exception
    {
        // Arrange
        String groupId1str     = GroupFakeBuilder.defaultId1.toString();
        String testedUri       = "/company/{id}";
        long   testedCompanyId = CompanyFakeBuilder.defaultId1;
        MultiValueMap<String, String> testedParams = new LinkedMultiValueMap<>()
        {{
            add("requestRelationIds", "1");
            add("requestRelationIds", "2");
            add("requestRelationIds", "3");
            add("requestRelationIds", "4");
            add("requestRelationIds", "5");
            add("requestRelationIds", "6");
        }};
        ResultMatcher expectedStatusCode = status().isOk();

        // Act
        ResultActions result = getMockMvc()
            .perform(MockMvcRequestBuilders
                .get(testedUri, testedCompanyId)
                .params(testedParams)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print());

        // Assert
        // @formatter:off
        String path_company_id     = "$.data.company.id";
        long   expected_company_id = CompanyFakeBuilder.defaultId1;

        String path_companyStatistics_companyId   = "$.data.companyStatistic.companyId";
        long expected_CompanyStatistics_companyId = CompanyFakeBuilder.defaultId1;
        // @todo: add test for: stackCount, teamsCount, reviewCount, technologiesCount

        String path_companyGroups_0_children              = "$.data.companyGroups[0].children";
        Object expected_companyGroups_0_children          = null;
        String path_companyGroups_0_recursiveGroup_id     = "$.data.companyGroups[0].recursiveGroup.id";
        long   expected_companyGroups_0_recursiveGroup_id = GroupFakeBuilder.defaultId1;

        String path_companyAddresses_companyId          = "$.data.companyAddresses.companyId";
        long   expected_companyAddresses_companyId      = CompanyFakeBuilder.defaultId1;
        String path_companyAddresses_addresses_0_id     = "$.data.companyAddresses.addresses[0].id";
        long   expected_companyAddresses_addresses_0_id = AddressFakeBuilder.defaultId1;

        // @todo: add maps to the sql

        String path_companyReviews_groupId1_id     = "$.data.companyReviews." + groupId1str + ".[0].id";
        long   expected_companyReviews_groupId1_id = ReviewFakeBuilder.defaultId1;

        String path_companyOwners_companyId     = "$.data.companyOwners.companyId";
        long   expected_companyOwners_companyId = CompanyFakeBuilder.defaultId1;
        String path_companyOwners_owners_0      = "$.data.companyOwners.owners[0]";
        String expected_companyOwners_owners_0  = IdsUserFakeBuilder.defaultId1;

        String path_companyUsers_userId1_id     = "$.data.companyUsers['" + IdsUserFakeBuilder.defaultId1 + "'].sub";
        String expected_companyUsers_userId1_id = IdsUserFakeBuilder.defaultId1;
        // @formatter:on

        result
            .andExpect(expectedStatusCode)
            .andExpect(jsonPath(path_company_id).value(expected_company_id))
            .andExpect(jsonPath(path_companyStatistics_companyId).value(expected_CompanyStatistics_companyId))
            .andExpect(jsonPath(path_companyGroups_0_children).value(expected_companyGroups_0_children))
            .andExpect(jsonPath(path_companyGroups_0_recursiveGroup_id).value(expected_companyGroups_0_recursiveGroup_id))
            .andExpect(jsonPath(path_companyAddresses_companyId).value(expected_companyAddresses_companyId))
            .andExpect(jsonPath(path_companyAddresses_addresses_0_id).value(expected_companyAddresses_addresses_0_id))
            .andExpect(jsonPath(path_companyReviews_groupId1_id).value(expected_companyReviews_groupId1_id))
            .andExpect(jsonPath(path_companyOwners_companyId).value(expected_companyOwners_companyId))
            .andExpect(jsonPath(path_companyOwners_owners_0).value(expected_companyOwners_owners_0))
            .andExpect(jsonPath(path_companyUsers_userId1_id).value(expected_companyUsers_userId1_id));
    }

    @Test
    public void callNotExistingCompany_returnStdApiError() throws Exception
    {
        // Arrange
        String        testedUri                  = "/company/{id}";
        long          testedNotExistingCompanyId = 123456;
        String        expectedData               = "Company not found.";
        boolean       expectedSuccess            = false;
        int           expectedErrorCode          = 1001;
        ResultMatcher expectedStatusCode         = status().isNotFound();

        // Act
        ResultActions result = getMockMvc().perform(MockMvcRequestBuilders
            .get(testedUri, testedNotExistingCompanyId)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));

        // Assert
        result
            .andExpect(expectedStatusCode)
            .andExpect(jsonPath("$.data").value(expectedData))
            .andExpect(jsonPath("$.success").value(expectedSuccess))
            .andExpect(jsonPath("$.errorCode").value(expectedErrorCode));
    }
}
