package com.kbalazsworks.stackjudge.unit.domain_aspects.aspects.slow_dervice_logger_aspect;

import com.kbalazsworks.stackjudge.AbstractTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.domain_aspects.services.SlowServiceLoggerAspectService;
import com.kbalazsworks.stackjudge.mocking.MockCreator;
import com.kbalazsworks.stackjudge.mocking.dummies.ProceedingJoinPointDummy;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

public class SlowServiceLoggerAspect_checkRunTimeTest extends AbstractTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    @Test
    @SneakyThrows
    public void checkingServiceCallWithParameter_calledNormally()
    {
        // Arrange
        ProceedingJoinPointDummy       testedAndExpectedJoinPoint = new ProceedingJoinPointDummy();
        SlowServiceLoggerAspectService mock                       = MockCreator.getSlowServiceLoggerAspectService();

        // Act
        serviceFactory.getSlowServiceLoggerAspect(mock).checkRunTime(testedAndExpectedJoinPoint);

        // Assert
        verify(mock, atLeastOnce()).checkRunTime(testedAndExpectedJoinPoint);
    }
}
