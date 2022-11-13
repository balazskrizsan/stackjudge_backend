package com.kbalazsworks.stackjudge.e2e.api.controllers.company_controller;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.kbalazsworks.stackjudge.AbstractE2eTest;
import com.kbalazsworks.stackjudge.integration.annotations.TruncateAllTables;
import com.kbalazsworks.stackjudge.mocking.IdsWireMocker;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CreateActionTest extends AbstractE2eTest
{
    private WireMockServer idsWireMockServer;

    @Before
    public void before()
    {
        idsWireMockServer = createStartAndGetIdsMockServer();
    }

    @After
    public void after()
    {
        idsWireMockServer.stop();
    }

    @Test
    @TruncateAllTables
    public void insertValidCompanyWithAddress_returns200ok() throws Exception
    {
        // Arrange
        IdsWireMocker.mockPostConnectToken(idsWireMockServer);
        IdsWireMocker.mockPostConnectIntrospect(idsWireMockServer);
        IdsWireMocker.mockGetApiAccountList(idsWireMockServer);

        String testedUri = "/company";
        MultiValueMap<String, String> testedParams = new LinkedMultiValueMap<>()
        {{
            add(
                "company",
                "{\"name\": \"c name\", \"domain\": \"company.com\", \"companySizeId\": 2, \"itSizeId\": 3}"
            );
            add(
                "address",
                "{\"fullAddress\": \"f addressa asdf asdf asdf \", \"markerLat\": 1.1, \"markerLng\": 2.2, \"manualMarkerLat\": 3.3, \"manualMarkerLng\": 4.4}"
            );
        }};
        ResultMatcher   expectedStatusCode = status().isOk();
        Matcher<Object> expectedData       = IsNull.nullValue();
        int             expectedErrorCode  = 0;
        HttpHeaders     httpHeaders        = IdsWireMocker.getHeadersForE2eTest();

        // Act
        ResultActions result = getMockMvcWithSecurity().perform(
            MockMvcRequestBuilders
                .multipart(testedUri)
                .headers(httpHeaders)
                .params(testedParams)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .accept(MediaType.APPLICATION_JSON)
        );

        // Assert
        result
            .andExpect(expectedStatusCode)
            .andExpect(jsonPath("$.data").value(expectedData))
            .andExpect(jsonPath("$.errorCode").value(expectedErrorCode));
    }

    @Test
    @TruncateAllTables
    public void insertInvalidCompanyWithAddress_returns4xxClientError() throws Exception
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
