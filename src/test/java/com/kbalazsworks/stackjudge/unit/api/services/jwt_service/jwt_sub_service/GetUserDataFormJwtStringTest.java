package com.kbalazsworks.stackjudge.unit.api.services.jwt_service.jwt_sub_service;

import com.kbalazsworks.stackjudge.AbstractTest;
import com.kbalazsworks.stackjudge.MockFactory;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.api.services.jwt_service.JwtSubService;
import io.jsonwebtoken.JwtException;
import nl.altindag.log.LogCaptor;
import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.platform.commons.JUnitException;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

public class GetUserDataFormJwtStringTest extends AbstractTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    private record TestData(int testedDataIndex, String expectedData)
    {
    }

    private TestData providerFor_calledWitValidToken_perfect(int repetition)
    {
        if (1 == repetition)
        {
            return new TestData(0, "123");
        }

        if (2 == repetition)
        {
            return new TestData(1, "MockUser Name");
        }

        if (3 == repetition)
        {
            return new TestData(2, "http://logo.com/1.jpg");
        }

        throw new JUnitException("TestData not found with repetition#" + repetition);
    }

    @RepeatedTest(3)
    public void calledWitValidToken_perfect(RepetitionInfo repetitionInfo)
    {
        // Arrange
        TestData testData            = providerFor_calledWitValidToken_perfect(repetitionInfo.getCurrentRepetition());
        String   testedRealTimeToken = serviceFactory.getJwtService().generateAccessToken(MockFactory.userMock);

        // Act
        String actualJwtData = serviceFactory
            .getJwtSubService()
            .getUserDataFormJwtString(testedRealTimeToken, testData.testedDataIndex);

        // Assert
        assertThat(actualJwtData).isEqualTo(testData.expectedData);
    }

    @Test
    public void callerWithInvalidOldToken_returnWithReturnedJwtException()
    {
        // Arrange
        String testedOldToken = MockFactory.JWT_FOR_DEFAULT_TEST_METHOD;

        // Act - Assert
        assertThatThrownBy(() -> serviceFactory
            .getJwtSubService()
            .getUserDataFormJwtString(testedOldToken, 1))
            .isInstanceOf(JwtException.class)
            .hasMessage("Invalid authentication error");
    }

    @Test
    public void callerWithInvalidToken_returnWithNewJwtExceptionAndWriteLog()
    {
        // Arrange
        LogCaptor logCaptor = LogCaptor.forClass(JwtSubService.class);

        String testedRealTimeToken    = serviceFactory.getJwtService().generateAccessToken(MockFactory.userMock);
        int    testedInvalidDataIndex = 10000;

        Class<JwtException> expectedClass   = JwtException.class;
        String              expectedMessage = "Invalid authentication error";
        String expectedErrorLog =
            "Jwt get user data error on id#10000; Index 10000 out of bounds for length 3";

        // Act - Assert
        assertAll(
            () -> assertThatThrownBy(() -> serviceFactory
                .getJwtSubService().getUserDataFormJwtString(testedRealTimeToken, testedInvalidDataIndex))
                .isInstanceOf(expectedClass).hasMessage(expectedMessage),
            () -> assertThat(logCaptor.getErrorLogs()).containsExactly(expectedErrorLog)
        );
    }
}
