package com.kbalazsworks.stackjudge.unit.domain_aspects.aspect_services.slow_service_logger_aspect_service;

import com.kbalazsworks.stackjudge.AbstractTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.domain_aspects.services.SlowServiceLoggerAspectService;
import com.kbalazsworks.stackjudge.mocking.setup_mock.ProceedingJoinPointMocker;
import com.kbalazsworks.stackjudge.mocking.setup_mock.SystemFactoryMocker;
import lombok.SneakyThrows;
import nl.altindag.log.LogCaptor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class SlowServiceLoggerAspectService_checkRunTimeTest extends AbstractTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    @Test
    @SneakyThrows
    public void halfSecondRun_willNotWriteLog()
    {
        // Arrange
        String testedJoinPontValue = "mock";
        String expectedReturn      = "mock";

        ProceedingJoinPoint jpMock = ProceedingJoinPointMocker.proceed_returns_object(testedJoinPontValue);

        // Act
        Object actualReturn = serviceFactory
            .getSlowServiceLoggerAspectService(SystemFactoryMocker.getCurrentTimeMillis_returns_multiCalledLongs(
                1000L,
                1500L
            ))
            .checkRunTime(jpMock);

        // Assert
        assertAll(
            () -> assertThat(expectedReturn).isEqualTo(actualReturn),
            () -> verify(jpMock, never()).getSignature()
        );
    }

    @Test
    @SneakyThrows
    public void oneAndAHalfSecondRun_willWriteWarnLog()
    {
        // Arrange
        String    testedJoinPontValue  = "mock";
        String    expectedReturn       = "mock";
        String    expectedErrorMessage = "Slow method run: 1500ms | dummy string answer";
        LogCaptor logCaptor            = LogCaptor.forClass(SlowServiceLoggerAspectService.class);

        ProceedingJoinPoint jpMock = ProceedingJoinPointMocker.proceed_returns_object(testedJoinPontValue);
        ProceedingJoinPointMocker.getSignature_returns_signatureDummy(jpMock);

        // Act
        Object actualReturn = serviceFactory
            .getSlowServiceLoggerAspectService(SystemFactoryMocker.getCurrentTimeMillis_returns_multiCalledLongs(
                1000L,
                2500L
            ))
            .checkRunTime(jpMock);

        // Assert
        assertAll(
            () -> assertThat(expectedReturn).isEqualTo(actualReturn),
            () -> assertThat(logCaptor.getWarnLogs()).containsExactly(expectedErrorMessage)
        );
    }

    @Test
    @SneakyThrows
    public void sixSecondRun_willWriteErrorLog()
    {
        // Arrange
        String    testedJoinPontValue  = "mock";
        String    expectedReturn       = "mock";
        String    expectedErrorMessage = "Critical slow method run: 6000ms | dummy string answer";
        LogCaptor logCaptor            = LogCaptor.forClass(SlowServiceLoggerAspectService.class);

        ProceedingJoinPoint jpMock = ProceedingJoinPointMocker.proceed_returns_object(testedJoinPontValue);
        ProceedingJoinPointMocker.getSignature_returns_signatureDummy(jpMock);

        // Act
        Object actualReturn = serviceFactory
            .getSlowServiceLoggerAspectService(SystemFactoryMocker.getCurrentTimeMillis_returns_multiCalledLongs(
                1000L,
                7000L
            ))
            .checkRunTime(jpMock);

        // Assert
        assertAll(
            () -> assertThat(expectedReturn).isEqualTo(actualReturn),
            () -> assertThat(logCaptor.getErrorLogs()).containsExactly(expectedErrorMessage)
        );
    }
}
