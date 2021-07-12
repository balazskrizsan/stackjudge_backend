package com.kbalazsworks.stackjudge.integration.domain.services.group_service;

import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.domain.services.GroupService;
import com.kbalazsworks.stackjudge.domain.value_objects.RecursiveGroup;
import com.kbalazsworks.stackjudge.domain.value_objects.RecursiveGroupTree;
import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.platform.commons.JUnitException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GroupServiceGenerateTreeStructureTest extends AbstractIntegrationTest
{
    @Autowired
    private GroupService groupService;

    private record TestData(
        List<RecursiveGroup> testedRecursiveGroups,
        List<RecursiveGroupTree> expectedRecursiveGroupTree
    )
    {
    }

    @Test
    public void VintageHack()
    {
        assertThat(true).isTrue();
    }

    private TestData dataProvider(int repetition)
    {
        if (repetition == 1)
        {
            RecursiveGroup rg1 = new RecursiveGroup(1L, "A1", (short) 11, 111L, null, 1, "1");
            RecursiveGroup rg2 = new RecursiveGroup(2L, "B1", (short) 22, 222L, 1L, 2, "1>2");
            RecursiveGroup rg3 = new RecursiveGroup(3L, "C1", (short) 22, 333L, 2L, 3, "1>2>3");
            RecursiveGroup rg4 = new RecursiveGroup(4L, "C2", (short) 44, 444L, 2L, 3, "1>2>3");
            RecursiveGroup rg5 = new RecursiveGroup(5L, "Q1", (short) 55, 555L, null, 1, "5");
            RecursiveGroup rg6 = new RecursiveGroup(6L, "W2", (short) 55, 666L, 5L, 2, "5>6");

            return new TestData(
                List.of(rg1, rg2, rg3, rg4, rg5, rg6),
                List.of(
                    new RecursiveGroupTree(rg1, List.of(
                        new RecursiveGroupTree(rg2, List.of(
                            new RecursiveGroupTree(rg3, null),
                            new RecursiveGroupTree(rg4, null)
                        )
                        )
                    )
                    ),
                    new RecursiveGroupTree(rg5, List.of(
                        new RecursiveGroupTree(rg6, null)
                    )
                    )
                )
            );
        }

        if (repetition == 2)
        {
            RecursiveGroup rg = new RecursiveGroup(1L, "A1", (short) 11, 111L, null, 1, "1");

            return new TestData(
                List.of(rg),
                List.of(new RecursiveGroupTree(rg, null))
            );
        }

        if (repetition == 3)
        {
            return new TestData(new ArrayList<>(), new ArrayList<>());
        }

        throw new JUnitException("Missing test data on repetition#" + repetition);
    }

    @RepeatedTest(3)
    public void perfect_perfect(RepetitionInfo repetitionInfo)
    {
        // Arrange
        TestData testData = dataProvider(repetitionInfo.getCurrentRepetition());

        // Act
        List<RecursiveGroupTree> result = groupService.generateTreeStructure(testData.testedRecursiveGroups);

        // Assert
        assertThat(result).isEqualTo(testData.expectedRecursiveGroupTree);
    }
}
