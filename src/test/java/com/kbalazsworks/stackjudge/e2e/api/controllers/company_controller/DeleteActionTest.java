package com.kbalazsworks.stackjudge.e2e.api.controllers.company_controller;

import com.kbalazsworks.stackjudge.AbstractE2eTest;
import com.kbalazsworks.stackjudge.fake_builders.CompanyFakeBuilder;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsNull;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DeleteActionTest extends AbstractE2eTest
{
    @Test
    @SqlGroup(
        {
            @Sql(
                executionPhase = BEFORE_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {
                    "classpath:test/sqls/_truncate_tables.sql",
                    "classpath:test/sqls/preset_add_1_company.sql"
                }
            ),
            @Sql(
                executionPhase = AFTER_TEST_METHOD,
                config = @SqlConfig(transactionMode = ISOLATED),
                scripts = {"classpath:test/sqls/_truncate_tables.sql"}
            )
        }
    )
    public void deleteExistingCompany_returns200ok() throws Exception
    {
        // Arrange
        String          testedUri          = "/company/{id}";
        long            testedCompanyId    = CompanyFakeBuilder.defaultId1;
        ResultMatcher   expectedStatusCode = status().isOk();
        Matcher<Object> expectedData       = IsNull.nullValue();
        boolean         expectedSuccess    = true;
        int             expectedErrorCode  = 0;

        // Act
        ResultActions result = getMockMvc().perform(
            MockMvcRequestBuilders
                .delete(testedUri, testedCompanyId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        // Assert
        result
            .andExpect(expectedStatusCode)
            .andExpect(jsonPath("$.data").value(expectedData))
            .andExpect(jsonPath("$.success").value(expectedSuccess))
            .andExpect(jsonPath("$.errorCode").value(expectedErrorCode));
    }
}
