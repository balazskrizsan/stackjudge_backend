package com.kbalazsworks.stackjudge.e2e.api.controllers.company_controller;

import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.integration.annotations.TruncateAllTables;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsNull;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CreateActionTest extends AbstractIntegrationTest
{
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup()
    {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    @TruncateAllTables
    public void insertValidCompanyWithAddress_returns200ok() throws Exception
    {
        // Arrange
        String testedUri = "/company";
        MultiValueMap<String, String> testedParams = new LinkedMultiValueMap<>()
        {{
            add("company", "{\"name\": \"c name\", \"companySizeId\":  2, \"itSizeId\": 3}");
            add(
                "address",
                "{\"fullAddress\": \"f addressa asdf asdf asdf \", \"markerLat\": 1.1, \"markerLng\": 2.2, \"manualMarkerLat\": 3.3, \"manualMarkerLng\": 4.4}"
            );
        }};
        ResultMatcher   expectedStatusCode = status().isOk();
        Matcher<Object> expectedData       = IsNull.nullValue();
        int             expectedErrorCode  = 0;

        // Act
        ResultActions act = mockMvc.perform(
            MockMvcRequestBuilders
                .multipart(testedUri)
                .params(testedParams)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .accept(MediaType.APPLICATION_JSON)
        );

        // Assert
        act
            .andExpect(expectedStatusCode)
            .andExpect(expectedStatusCode)
            .andExpect(jsonPath("$.data").value(expectedData))
            .andExpect(jsonPath("$.errorCode").value(expectedErrorCode));
    }

    @Test
    @TruncateAllTables
    public void insertInvalidCompanyWithAddress_returns200ok() throws Exception
    {
        // Arrange
        String testedUri = "/company";
        MultiValueMap<String, String> testedParams = new LinkedMultiValueMap<>()
        {{
            add("company", "{\"name\": \"c name\"}");
            add(
                "address",
                "{\"fullAddress\": \"f addressa asdf asdf asdf \"}"
            );
        }};
        ResultMatcher expectedStatusCode = status().is4xxClientError();
        String        expectedData       = "Validation error.";
        int           expectedErrorCode  = 2;

        // Act
        ResultActions act = mockMvc.perform(
            MockMvcRequestBuilders
                .multipart(testedUri)
                .params(testedParams)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .accept(MediaType.APPLICATION_JSON)
        );

        // Assert
        act
            .andExpect(expectedStatusCode)
            .andExpect(jsonPath("$.data").value(expectedData))
            .andExpect(jsonPath("$.errorCode").value(expectedErrorCode));
    }
}
