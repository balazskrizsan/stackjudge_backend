package com.kbalazsworks.stackjudge.e2e.api.controllers.company_controller;

import com.kbalazsworks.stackjudge.AbstractE2eTest;
import com.kbalazsworks.stackjudge.fake_builders.AddressFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.CompanyFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.GroupFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.ReviewFakeBuilder;
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

public class SearchActionTest extends AbstractE2eTest
{
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
        }};
        ResultMatcher expectedStatusCode            = status().isOk();
        String        expectedCompanyId             = CompanyFakeBuilder.defaultId1.toString();
        long          expectedCompanyStatisticId    = CompanyFakeBuilder.defaultId1;
        long          expectedRecursiveGroupId      = GroupFakeBuilder.defaultId1;
        long          expectedCompanyAddressesId    = AddressFakeBuilder.defaultId1;
        String        expectedCompanyReviewsGroupId = GroupFakeBuilder.defaultId1.toString();
        long          expectedCompanyReviewsId      = ReviewFakeBuilder.defaultId1;

        // Act
        ResultActions result = getMockMvc().perform(
            MockMvcRequestBuilders
                .get(testedUri)
                .params(testedParams)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        // Assert
        String companyStatisticsPath = "$.data.companyStatistics." + expectedCompanyId + ".companyId";
        String companyGroupsPath     = "$.data.companyGroups." + expectedCompanyId + "[0].recursiveGroup.id";
        String companyAddressesPath  = "$.data.companyAddresses." + expectedCompanyId + "[0].id";
        String companyReviewsPath = "$.data.companyReviews." + expectedCompanyId + "."
            + expectedCompanyReviewsGroupId + ".[0].id";

        result
            .andExpect(expectedStatusCode)
            .andExpect(jsonPath("$.data.companies[0].id").value(expectedCompanyId))
            .andExpect(jsonPath(companyStatisticsPath).value(expectedCompanyStatisticId))
            .andExpect(jsonPath(companyGroupsPath).value(expectedRecursiveGroupId))
            .andExpect(jsonPath(companyAddressesPath).value(expectedCompanyAddressesId))
            .andExpect(jsonPath(companyReviewsPath).value(expectedCompanyReviewsId));
    }
}
