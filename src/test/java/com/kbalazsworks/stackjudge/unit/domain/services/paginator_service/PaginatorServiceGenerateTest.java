package com.kbalazsworks.stackjudge.unit.domain.services.paginator_service;

import com.kbalazsworks.stackjudge.AbstractTest;
import com.kbalazsworks.stackjudge.domain.enums.paginator.ItemTypeEnum;
import com.kbalazsworks.stackjudge.domain.enums.paginator.NavigationEnum;
import com.kbalazsworks.stackjudge.domain.services.PaginatorService;
import com.kbalazsworks.stackjudge.domain.value_objects.PaginatorItem;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.platform.commons.JUnitException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PaginatorServiceGenerateTest extends AbstractTest
{
    @Autowired
    private PaginatorService paginatorService;

    private record TestData(
        long testedElementsBeforeSeekId,
        long testedFullLength,
        short testedLimit,
        List<PaginatorItem> expectedList
    )
    {
    }

    @Test
    public void vintageHack()
    {
        Assert.assertTrue(true);
    }

    private TestData provider(int repetition) throws Exception
    {
        if (repetition == 1)
        {
            return new TestData(0, 1, (short) 10, List.of(
                new PaginatorItem(ItemTypeEnum.PAGE, "1", NavigationEnum.FIRST, true)
            ));
        }
        if (repetition == 2)
        {
            return new TestData(4, 10, (short) 10, List.of(
                new PaginatorItem(ItemTypeEnum.PAGE, "1", NavigationEnum.FIRST, true)
            ));
        }

        if (repetition == 3)
        {
            return new TestData(14, 20, (short) 10, List.of(
                new PaginatorItem(ItemTypeEnum.PAGE, "1", NavigationEnum.FIRST, false),
                new PaginatorItem(ItemTypeEnum.PAGE, "2", NavigationEnum.SECOND, true)
            ));
        }

        if (repetition == 4)
        {
            return new TestData(4, 20, (short) 10, List.of(
                new PaginatorItem(ItemTypeEnum.PAGE, "1", NavigationEnum.FIRST, true),
                new PaginatorItem(ItemTypeEnum.PAGE, "2", NavigationEnum.SECOND, false)
            ));
        }

        if (repetition == 5)
        {
            return new TestData(14, 30, (short) 10, List.of(
                new PaginatorItem(ItemTypeEnum.PAGE, "1", NavigationEnum.FIRST, false),
                new PaginatorItem(ItemTypeEnum.PAGE, "2", NavigationEnum.SECOND, true),
                new PaginatorItem(ItemTypeEnum.PAGE, "3", NavigationEnum.CURRENT_PLUS_1, false)
            ));
        }

        if (repetition == 6)
        {
            return new TestData(14, 40, (short) 10, List.of(
                new PaginatorItem(ItemTypeEnum.PAGE, "1", NavigationEnum.FIRST, false),
                new PaginatorItem(ItemTypeEnum.PAGE, "2", NavigationEnum.SECOND, true),
                new PaginatorItem(ItemTypeEnum.PAGE, "3", NavigationEnum.CURRENT_PLUS_1, false),
                new PaginatorItem(ItemTypeEnum.PAGE, "4", NavigationEnum.CURRENT_PLUS_2, false)
            ));
        }

        if (repetition == 7)
        {
            return new TestData(14, 50, (short) 10, List.of(
                new PaginatorItem(ItemTypeEnum.PAGE, "1", NavigationEnum.FIRST, false),
                new PaginatorItem(ItemTypeEnum.PAGE, "2", NavigationEnum.SECOND, true),
                new PaginatorItem(ItemTypeEnum.PAGE, "3", NavigationEnum.CURRENT_PLUS_1, false),
                new PaginatorItem(ItemTypeEnum.PAGE, "4", NavigationEnum.CURRENT_PLUS_2, false),
                new PaginatorItem(ItemTypeEnum.PAGE, "5", NavigationEnum.LAST, false)
            ));
        }

        if (repetition == 8)
        {
            return new TestData(14, 60, (short) 10, List.of(
                new PaginatorItem(ItemTypeEnum.PAGE, "1", NavigationEnum.FIRST, false),
                new PaginatorItem(ItemTypeEnum.PAGE, "2", NavigationEnum.SECOND, true),
                new PaginatorItem(ItemTypeEnum.PAGE, "3", NavigationEnum.CURRENT_PLUS_1, false),
                new PaginatorItem(ItemTypeEnum.PAGE, "4", NavigationEnum.CURRENT_PLUS_2, false),
                new PaginatorItem(ItemTypeEnum.PAGE, "5", NavigationEnum.LAST_MINUS_1, false),
                new PaginatorItem(ItemTypeEnum.PAGE, "6", NavigationEnum.LAST, false)
            ));
        }

        if (repetition == 9)
        {
            return new TestData(14, 70, (short) 10, List.of(
                new PaginatorItem(ItemTypeEnum.PAGE, "1", NavigationEnum.FIRST, false),
                new PaginatorItem(ItemTypeEnum.PAGE, "2", NavigationEnum.SECOND, true),
                new PaginatorItem(ItemTypeEnum.PAGE, "3", NavigationEnum.CURRENT_PLUS_1, false),
                new PaginatorItem(ItemTypeEnum.PAGE, "4", NavigationEnum.CURRENT_PLUS_2, false),
                new PaginatorItem(ItemTypeEnum.SPACER, "", null, false),
                new PaginatorItem(ItemTypeEnum.PAGE, "6", NavigationEnum.LAST_MINUS_1, false),
                new PaginatorItem(ItemTypeEnum.PAGE, "7", NavigationEnum.LAST, false)
            ));
        }

        if (repetition == 10)
        {
            return new TestData(49, 200, (short) 5, List.of(
                new PaginatorItem(ItemTypeEnum.PAGE, "1", NavigationEnum.FIRST, false),
                new PaginatorItem(ItemTypeEnum.PAGE, "2", NavigationEnum.SECOND, false),
                new PaginatorItem(ItemTypeEnum.SPACER, "", null, false),
                new PaginatorItem(ItemTypeEnum.PAGE, "8", NavigationEnum.CURRENT_MINUS_2, false),
                new PaginatorItem(ItemTypeEnum.PAGE, "9", NavigationEnum.CURRENT_MINUS_1, false),
                new PaginatorItem(ItemTypeEnum.PAGE, "10", NavigationEnum.CURRENT, true),
                new PaginatorItem(ItemTypeEnum.PAGE, "11", NavigationEnum.CURRENT_PLUS_1, false),
                new PaginatorItem(ItemTypeEnum.PAGE, "12", NavigationEnum.CURRENT_PLUS_2, false),
                new PaginatorItem(ItemTypeEnum.SPACER, "", null, false),
                new PaginatorItem(ItemTypeEnum.PAGE, "39", NavigationEnum.LAST_MINUS_1, false),
                new PaginatorItem(ItemTypeEnum.PAGE, "40", NavigationEnum.LAST, false)
            ));
        }

        throw new JUnitException("TestData not found with repetition#" + repetition);
    }

    @RepeatedTest(value = 10, name = RepeatedTest.LONG_DISPLAY_NAME)
    public void multipleGoodInputs_perfect(RepetitionInfo repetitionInfo) throws Exception
    {
        // Arrange
        TestData testData = provider(repetitionInfo.getCurrentRepetition());

        // Act
        List<PaginatorItem> actualList = paginatorService.generate(
            testData.testedElementsBeforeSeekId(),
            testData.testedFullLength(),
            testData.testedLimit()
        );

        // Assert
        assertThat(testData.expectedList()).isEqualTo(actualList);
    }
}
