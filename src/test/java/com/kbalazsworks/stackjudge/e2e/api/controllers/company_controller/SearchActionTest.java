package com.kbalazsworks.stackjudge.e2e.api.controllers.company_controller;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.kbalazsworks.stackjudge.AbstractE2eTest;
import com.kbalazsworks.stackjudge.fake_builders.AddressFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.CompanyFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.GroupFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.IdsUserFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.ReviewFakeBuilder;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SearchActionTest extends AbstractE2eTest
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
    @SqlGroup(
        {
            @Sql(
                executionPhase = BEFORE_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {
                    "classpath:test/sqls/_truncate_tables.sql",
                    "classpath:test/sqls/preset_add_1_company.sql",
                    "classpath:test/sqls/preset_add_1_address.sql",
                    "classpath:test/sqls/preset_add_1_group.sql",
                    "classpath:test/sqls/preset_add_1_review.sql",
                    "classpath:test/sqls/preset_add_1_user.sql",
                    "classpath:test/sqls/preset_add_1_company_owner.sql",
                }
            ),
            @Sql(
                executionPhase = AFTER_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql"}
            )
        }
    )
    public void callSearchWithFullyPreparedDb_willReturnAllEntities() throws Exception
    {
        // Arrange
        String companyId1Str = CompanyFakeBuilder.defaultId1.toString();
        String groupId1str   = GroupFakeBuilder.defaultId1.toString();

        String testedUri       = "/company";
        Long   testedCompanyId = CompanyFakeBuilder.defaultId1;
        MultiValueMap<String, String> testedParams = new LinkedMultiValueMap<>()
        {{
            add("seekId", testedCompanyId.toString());
            add("limit", "8");
            add("requestRelationIds", "1");
            add("requestRelationIds", "2");
            add("requestRelationIds", "3");
            add("requestRelationIds", "4");
            add("requestRelationIds", "5");
            add("requestRelationIds", "6");
        }};

        // @formatter:off
        ResultMatcher expectedStatusCode = status().isOk();

        String path_companies_0_id     = "$.data.companies[0].id";
        long   expected_companies_0_id = CompanyFakeBuilder.defaultId1;

        String path_companyGroups_companyId1_0_children              = "$.data.companyGroups." + companyId1Str + "[0].children";
        Object expected_companyGroups_companyId1_0_children          = null;
        String path_companyGroups_companyId1_0_recursiveGroup_id     = "$.data.companyGroups." + companyId1Str + "[0].recursiveGroup.id";
        long   expected_companyGroups_companyId1_0_recursiveGroup_id = GroupFakeBuilder.defaultId1;

        String path_paginator_0_typeId         = "$.data.paginator[0].typeId";
        long   expected_paginator_0_typeId     = 1;
        String path_paginator_0_pageNumber     = "$.data.paginator[0].pageNumber";
        long   expected_paginator_0_pageNumber = 1;
        String path_paginator_0_navigation     = "$.data.paginator[0].navigation";
        long   expected_paginator_0_navigation = 1;
        String path_paginator_0_active         = "$.data.paginator[0].active";
        boolean   expected_paginator_0_active  = true;

        String path_newSeekId     = "$.data.newSeekId";
        long   expected_newSeekId = CompanyFakeBuilder.defaultId1;

        String path_companyStatistics_companyId1_companyId     = "$.data.companyStatistics." + companyId1Str + ".companyId";
        long   expected_CompanyStatistics_companyId1_companyId = CompanyFakeBuilder.defaultId1;
        // @todo: add test for: stackCount, teamsCount, reviewCount, technologiesCount

        String path_companyAddresses_companyId1_companyId          = "$.data.companyAddresses." + companyId1Str + ".companyId";
        long   expected_companyAddresses_companyId1_companyId      = CompanyFakeBuilder.defaultId1;
        String path_companyAddresses_companyId1_addresses_0_id     = "$.data.companyAddresses." + companyId1Str + ".addresses[0].id";
        long   expected_companyAddresses_companyId1_addresses_0_id = AddressFakeBuilder.defaultId1;

        // @todo: add maps to the sql

        String path_companyReviews_companyId1_groupId1_id     = "$.data.companyReviews." + companyId1Str + "." + groupId1str + ".[0].id";
        long   expected_companyReviews_companyId1_groupId1_id = ReviewFakeBuilder.defaultId1;

        String path_companyOwners_companyId1_companyId     = "$.data.companyOwners." + companyId1Str + ".companyId";
        long   expected_companyOwners_companyId1_companyId = CompanyFakeBuilder.defaultId1;
        String path_companyOwners_companyId1_owners_0      = "$.data.companyOwners." + companyId1Str + ".owners[0]";
        String expected_companyOwners_companyId1_owners_0  = IdsUserFakeBuilder.defaultId1;

        String path_companyUsers_idsUserId1_idsUserId1     = "$.data.companyUsers['".concat(IdsUserFakeBuilder.defaultId1) + "'].sub";
        String expected_companyUsers_idsUserId1_idsUserId1 = IdsUserFakeBuilder.defaultId1;
        // @formatter:on

        // Act
        ResultActions result = getMockMvc()
            .perform(
                MockMvcRequestBuilders
                    .get(testedUri)
                    .params(testedParams)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
            );

        // Assert
        // @formatter:off
        result
            .andExpect(expectedStatusCode)
            .andExpect(jsonPath(path_companies_0_id).value(expected_companies_0_id))
            .andExpect(jsonPath(path_companyGroups_companyId1_0_children).value(expected_companyGroups_companyId1_0_children))
            .andExpect(jsonPath(path_companyGroups_companyId1_0_recursiveGroup_id).value(expected_companyGroups_companyId1_0_recursiveGroup_id))
            .andExpect(jsonPath(path_paginator_0_typeId).value(expected_paginator_0_typeId))
            .andExpect(jsonPath(path_paginator_0_pageNumber).value(expected_paginator_0_pageNumber))
            .andExpect(jsonPath(path_paginator_0_navigation).value(expected_paginator_0_navigation))
            .andExpect(jsonPath(path_paginator_0_active).value(expected_paginator_0_active))
            .andExpect(jsonPath(path_newSeekId).value(expected_newSeekId))
            .andExpect(jsonPath(path_companyStatistics_companyId1_companyId).value(expected_CompanyStatistics_companyId1_companyId))
            .andExpect(jsonPath(path_companyAddresses_companyId1_companyId).value(expected_companyAddresses_companyId1_companyId))
            .andExpect(jsonPath(path_companyAddresses_companyId1_addresses_0_id).value(expected_companyAddresses_companyId1_addresses_0_id))
            .andExpect(jsonPath(path_companyReviews_companyId1_groupId1_id).value(expected_companyReviews_companyId1_groupId1_id))
            .andExpect(jsonPath(path_companyOwners_companyId1_companyId).value(expected_companyOwners_companyId1_companyId))
            .andExpect(jsonPath(path_companyOwners_companyId1_owners_0).value(expected_companyOwners_companyId1_owners_0))
            .andExpect(jsonPath(path_companyUsers_idsUserId1_idsUserId1).value(expected_companyUsers_idsUserId1_idsUserId1));
        // @formatter:on
    }
}
