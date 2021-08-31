package com.kbalazsworks.stackjudge.unit.common.services.secure_random_service;

import com.kbalazsworks.stackjudge.AbstractTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GetTest extends AbstractTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    @Test
    public void vintageHack()
    {
        assertTrue(true);
    }

    private record TestData(int testedLength, int expectedLength)
    {
    }

    private TestData provider(int repetition)
    {
        if (1 == repetition)
        {
            return new TestData(0, 0);
        }

        if (2 == repetition)
        {
            return new TestData(20, 20);
        }

        if (3 == repetition)
        {
            return new TestData(1000, 1000);
        }

        throw getRepeatException(repetition);
    }

    @RepeatedTest(3)
    public void testingWithDifferentLength_perfect(RepetitionInfo repetitionInfo)
    {
        // Arrange
        TestData testData = provider(repetitionInfo.getCurrentRepetition());

        // Act
        String actualRandom = serviceFactory.getSecureRandomService().get(testData.testedLength);

        // Assert
        assertThat(actualRandom).hasSize(testData.expectedLength);
    }
}
