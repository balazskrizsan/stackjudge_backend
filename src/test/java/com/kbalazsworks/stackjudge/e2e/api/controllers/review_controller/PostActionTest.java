package com.kbalazsworks.stackjudge.e2e.api.controllers.review_controller;

import com.kbalazsworks.stackjudge.AbstractE2eTest;
import com.kbalazsworks.stackjudge.MockFactory;
import com.kbalazsworks.stackjudge.domain.entities.Review;
import com.kbalazsworks.stackjudge.domain.services.ReviewService;
import com.kbalazsworks.stackjudge.fake_builders.ReviewFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.UserFakeBuilder;
import com.kbalazsworks.stackjudge.integration.annotations.TruncateAllTables;
import com.kbalazsworks.stackjudge.state.services.StateService;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostActionTest extends AbstractE2eTest
{
    @MockBean
    private ReviewService reviewService;

    @MockBean
    private StateService stateService;

    @Test
    @TruncateAllTables
    public void insertOneValidReview_returns200ok() throws Exception
    {
        // Arrange
        MockFactory.SessionService_getStateMock(stateService);

        String testedUri = "/review";
        MultiValueMap<String, String> testedPostData = new LinkedMultiValueMap<>()
        {{
            add("group_id", "101001");
            add("visibility", "1");
            add("rate", "2");
            add("review", "long review text");
        }};
        ResultMatcher expectedStatusCode = status().isOk();
        Review expectedReview = new ReviewFakeBuilder()
            .id(null)
            .createdAt(MockFactory.localDateTimeMock)
            .createdBy(UserFakeBuilder.defaultId1)
            .build();

        // Act
        ResultActions result = getMockMvc().perform(
            MockMvcRequestBuilders
                .post(testedUri)
                .params(testedPostData)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .accept(MediaType.APPLICATION_JSON)
        );

        // Assert
        result
            .andExpect(expectedStatusCode);

        verify(reviewService).create(expectedReview);
    }
}
