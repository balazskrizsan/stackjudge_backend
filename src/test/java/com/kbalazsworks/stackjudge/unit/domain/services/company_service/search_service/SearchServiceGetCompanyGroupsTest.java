package com.kbalazsworks.stackjudge.unit.domain.services.company_service.search_service;

import com.kbalazsworks.stackjudge.AbstractTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.domain.services.GroupService;
import com.kbalazsworks.stackjudge.domain.value_objects.RecursiveGroup;
import com.kbalazsworks.stackjudge.domain.value_objects.RecursiveGroupTree;
import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.platform.commons.JUnitException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@RequiredArgsConstructor
public class SearchServiceGetCompanyGroupsTest extends AbstractTest
{
    public final ServiceFactory serviceFactory;

    @Test
    public void vintageHack()
    {
        assertTrue(true);
    }

    private record TestData(
        List<Long> testedCompanyIds,
        Map<Long, List<RecursiveGroupTree>> expectedCompanyGroups,
        List<RecursiveGroupTree> mockForGenerateTreeStructure
    )
    {
    }

    private TestData provider(int repetition)
    {
        if (repetition == 1)
        {
            RecursiveGroup rg1 = new RecursiveGroup(1L, "name1", (short) 11, 111L, 12L, 13, "path1");
            RecursiveGroup rg2 = new RecursiveGroup(2L, "name2", (short) 22, 333L, 22L, 23, "path1");
            RecursiveGroup rg3 = new RecursiveGroup(3L, "name3", (short) 33, 333L, 32L, 33, "path3");

            return new TestData(
                List.of(1L, 3L),
                Map.of(
                    111L, List.of(new RecursiveGroupTree(rg1, null)),
                    333L, List.of(
                        new RecursiveGroupTree(rg2, null),
                        new RecursiveGroupTree(rg3, null)
                    )
                ),
                List.of(
                    new RecursiveGroupTree(rg1, null),
                    new RecursiveGroupTree(rg2, null),
                    new RecursiveGroupTree(rg3, null)
                )
            );
        }

        if (repetition == 2)
        {
            return new TestData(new ArrayList<>(), new HashMap<>(), new ArrayList<>());
        }

        throw new JUnitException("Missing test data on repetition#" + repetition);
    }

    @RepeatedTest(2)
    public void createStructureFromMockData_perfect(RepetitionInfo repetitionInfo)
    {
        // Arrange
        TestData testData = provider(repetitionInfo.getCurrentRepetition());

        GroupService groupServiceMock = mock(GroupService.class);
        when(groupServiceMock.generateTreeStructure(any())).thenReturn(testData.mockForGenerateTreeStructure);

        // Act
        Map<Long, List<RecursiveGroupTree>> actualCompanyGroups = serviceFactory
            .getSearchService(groupServiceMock)
            .getCompanyGroups(testData.testedCompanyIds);

        // Assert
        assertAll(
            () -> verify(groupServiceMock, times(1)).recursiveSearch(testData.testedCompanyIds),
            () -> assertThat(actualCompanyGroups).isEqualTo(testData.expectedCompanyGroups)
        );
    }
}
