package com.kbalazsworks.stackjudge.unit.domain.review_module.servies;

import com.kbalazsworks.stackjudge.AbstractTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.domain.review_module.entities.Review;
import com.kbalazsworks.stackjudge.fake_builders.CompanyFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.GroupFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.ReviewFakeBuilder;
import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.platform.commons.JUnitException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReviewService_maskProtectedReviewCreatedBysTest extends AbstractTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    @Test
    public void vintageHack()
    {
        assertTrue(true);
    }

    private record TestData(
        Map<Long, Map<Long, List<Review>>> testedReviews,
        Map<Long, Map<Long, List<Review>>> expectedReviews
    )
    {
    }

    private TestData provider(int repetition)
    {
        if (1 == repetition)
        {
            return new TestData(new HashMap<>(), new HashMap<>());
        }

        if (2 == repetition)
        {
            return new TestData(
                new HashMap<>()
                {{
                    put(CompanyFakeBuilder.defaultId1, new HashMap<>()
                        {{
                            put(GroupFakeBuilder.defaultId1, new ArrayList<>()
                                {{
                                    add(new ReviewFakeBuilder().build());
                                }}
                            );
                        }}
                    );
                }},
                Map.of(
                    CompanyFakeBuilder.defaultId1,
                    Map.of(
                        GroupFakeBuilder.defaultId1,
                        List.of(new ReviewFakeBuilder().build())
                    )
                )
            );
        }

        if (3 == repetition)
        {
            return new TestData(
                new HashMap<>()
                {{
                    put(CompanyFakeBuilder.defaultId1, new HashMap<>()
                        {{
                            put(GroupFakeBuilder.defaultId1, new ArrayList<>()
                                {{
                                    add(new ReviewFakeBuilder().visibility((short) 3).build());
                                    add(new ReviewFakeBuilder().build());
                                }}
                            );
                        }}
                    );
                    put(CompanyFakeBuilder.defaultId2, new HashMap<>()
                        {{
                            put(GroupFakeBuilder.defaultId3, new ArrayList<>()
                                {{
                                    add(new ReviewFakeBuilder().visibility((short) 3).build());
                                    add(new ReviewFakeBuilder().build());
                                }}
                            );
                        }}
                    );
                }},
                Map.of(
                    CompanyFakeBuilder.defaultId1,
                    Map.of(
                        GroupFakeBuilder.defaultId1,
                        List.of(
                            new ReviewFakeBuilder().visibility((short) 3).createdBy("").build(),
                            new ReviewFakeBuilder().build()
                        )
                    ),
                    CompanyFakeBuilder.defaultId2,
                    Map.of(
                        GroupFakeBuilder.defaultId3,
                        List.of(
                            new ReviewFakeBuilder().visibility((short) 3).createdBy("").build(),
                            new ReviewFakeBuilder().build()
                        )
                    )
                )
            );
        }

        throw new JUnitException("Missing test data on repetition#" + repetition);
    }

    @RepeatedTest(3)
    public void callingWithList_returnsByProvider(RepetitionInfo repetitionInfo)
    {
        // Arrange
        TestData testData = provider(repetitionInfo.getCurrentRepetition());

        // Act
        Map<Long, Map<Long, List<Review>>> actualResponse = serviceFactory
            .getReviewService()
            .maskProtectedReviewCreatedBys(testData.testedReviews());

        // Assert
        assertThat(actualResponse).isEqualTo(testData.expectedReviews());
    }
}
