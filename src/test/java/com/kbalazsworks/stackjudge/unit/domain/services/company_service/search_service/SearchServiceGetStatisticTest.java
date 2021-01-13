package com.kbalazsworks.stackjudge.unit.domain.services.company_service.search_service;

import com.kbalazsworks.stackjudge.AbstractTest;
import com.kbalazsworks.stackjudge.domain.services.GroupService;
import com.kbalazsworks.stackjudge.domain.services.company_services.SearchService;
import com.kbalazsworks.stackjudge.domain.value_objects.CompanyStatistic;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.platform.commons.JUnitException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SearchServiceGetStatisticTest extends AbstractTest
{
    @Autowired
    private GroupService groupService;

    @Autowired
    SearchService searchService;

    @BeforeEach
    @AfterEach
    public void clean()
    {
        searchService.setGroupService(groupService);
    }

    @Test
    public void vintageHack()
    {
        Assert.assertTrue(true);
    }

    private record TestData(
        List<Long> testedCompanyIds,
        Map<Long, CompanyStatistic> expectedStatistic,
        Map<Long, Integer> mockForCountStack,
        Map<Long, Integer> mockForCountTeams
    )
    {
    }

    private TestData provider(int repetition)
    {
        if (repetition == 1)
        {
            return new TestData(
                List.of(1L, 3L, 5L),
                Map.of(
                    1L, new CompanyStatistic(1L, 12, 13, 0, 0),
                    3L, new CompanyStatistic(3L, 22, 23, 0, 0),
                    5L, new CompanyStatistic(5L, 32, 33, 0, 0)
                ),
                Map.of(
                    1L, 12,
                    3L, 22,
                    5L, 32
                ),
                Map.of(
                    1L, 13,
                    3L, 23,
                    5L, 33
                )
            );
        }

        if (repetition == 2)
        {
            return new TestData(new ArrayList<>(), new HashMap<>(), new HashMap<>(), new HashMap<>());
        }

        throw new JUnitException("Missing test data on repetition#" + repetition);
    }

    @RepeatedTest(2)
    public void createStructureFromMockData_perfect(RepetitionInfo repetitionInfo)
    {
        // Arrange
        TestData testData = provider(repetitionInfo.getCurrentRepetition());

        GroupService groupServiceMock = mock(GroupService.class);
        when(groupServiceMock.countStacks(testData.testedCompanyIds)).thenReturn(testData.mockForCountStack);
        when(groupServiceMock.countTeams(testData.testedCompanyIds)).thenReturn(testData.mockForCountTeams);

        searchService.setGroupService(groupServiceMock);

        // Act
        Map<Long, CompanyStatistic> actualStatistic = searchService.getStatistic(testData.testedCompanyIds);

        // Assert
        assertThat(actualStatistic).isEqualTo(testData.expectedStatistic);
    }
}
