package com.kbalazsworks.stackjudge.e2e.api.controllers.review_controller;

import com.kbalazsworks.stackjudge.AbstractE2eTest;
import com.kbalazsworks.stackjudge.MockFactory;
import com.kbalazsworks.stackjudge.domain.entities.Review;
import com.kbalazsworks.stackjudge.domain.services.ReviewService;
import com.kbalazsworks.stackjudge.integration.annotations.TruncateAllTables;
import com.kbalazsworks.stackjudge.session.services.SessionService;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostActionTest extends AbstractE2eTest
{
    @MockBean
    private ReviewService reviewService;

    @MockBean
    private SessionService sessionService;

    @Test
    @TruncateAllTables
    public void insertOneValidReview_returns200ok() throws Exception
    {
        // Arrange
        MockFactory.SessionService_getSessionStateMock(sessionService);

        String testedUri = "/review";
        MultiValueMap<String, String> testedPostData = new LinkedMultiValueMap<>()
        {{
            add("group_id", "1");
            add("visibility", "2");
            add("rate", "3");
            add("review", "review text");
        }};
        ResultMatcher expectedStatusCode = status().isOk();
        Review expectedReview = new Review(
            null,
            1L,
            (short) 2,
            (short) 3,
            "review text",
            LocalDateTime.of(2001, 1, 2, 3, 4),
            3L
        );

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
