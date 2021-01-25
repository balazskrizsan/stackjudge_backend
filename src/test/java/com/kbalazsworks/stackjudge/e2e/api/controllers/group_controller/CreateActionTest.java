package com.kbalazsworks.stackjudge.e2e.api.controllers.group_controller;

import com.kbalazsworks.stackjudge.AbstractE2eTest;
import com.kbalazsworks.stackjudge.fake_builders.CompanyFakeBuilder;
import com.kbalazsworks.stackjudge.integration.annotations.TruncateAllTables;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.context.jdbc.SqlConfig.TransactionMode.ISOLATED;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CreateActionTest extends AbstractE2eTest
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
    public void insertValidGroup_returns200ok() throws Exception
    {
        // Arrange
        String testedUri = "/group";
        MultiValueMap<String, String> testedParams = new LinkedMultiValueMap<>()
        {{
            add("companyId", CompanyFakeBuilder.defaultId1.toString());
            add("typeId", "1");
            add("name", "name");
            add("membersOnStackId", "2");
        }};
        ResultMatcher   expectedStatusCode = status().isOk();
        Matcher<Object> expectedData       = IsNull.nullValue();
        boolean         expectedSuccess    = true;
        int             expectedErrorCode  = 0;

        // Act
        ResultActions result = getMockMvc().perform(
            MockMvcRequestBuilders
                .multipart(testedUri)
                .params(testedParams)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .accept(MediaType.APPLICATION_JSON)
        );

        // Assert
        result
            .andExpect(expectedStatusCode)
            .andExpect(jsonPath("$.data").value(expectedData))
            .andExpect(jsonPath("$.success").value(expectedSuccess))
            .andExpect(jsonPath("$.errorCode").value(expectedErrorCode));
    }

    @Test
    @TruncateAllTables
    public void insertInvalidGroup_returns4xxClientError() throws Exception
    {
        // Arrange
        String testedUri = "/group";
        MultiValueMap<String, String> testedParams = new LinkedMultiValueMap<>()
        {{
            add("companyId", CompanyFakeBuilder.defaultId1.toString());
            add("typeId", "1");
            add("name", "");
            add("membersOnStackId", "2");
        }};
        ResultMatcher expectedStatusCode = status().is4xxClientError();
        String        expectedData       = "Validation error.";
        boolean       expectedSuccess    = false;
        int           expectedErrorCode  = 2;

        // Act
        ResultActions result = getMockMvc().perform(
            MockMvcRequestBuilders
                .multipart(testedUri)
                .params(testedParams)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .accept(MediaType.APPLICATION_JSON)
        );

        // Assert
        result
            .andExpect(expectedStatusCode)
            .andExpect(jsonPath("$.data").value(expectedData))
            .andExpect(jsonPath("$.success").value(expectedSuccess))
            .andExpect(jsonPath("$.errorCode").value(expectedErrorCode));
    }

    @Test
    @TruncateAllTables
    public void insertWithMissingCompany_returns4xxClientError() throws Exception
    {
        // Arrange
        String testedUri = "/group";
        MultiValueMap<String, String> testedParams = new LinkedMultiValueMap<>()
        {{
            add("companyId", CompanyFakeBuilder.defaultId1.toString());
            add("typeId", "1");
            add("name", "");
            add("membersOnStackId", "2");
        }};
        ResultMatcher expectedStatusCode = status().is4xxClientError();
        String        expectedData       = "Validation error.";
        boolean       expectedSuccess    = false;
        int           expectedErrorCode  = 2;

        // Act
        ResultActions result = getMockMvc().perform(
            MockMvcRequestBuilders
                .multipart(testedUri)
                .params(testedParams)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
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
