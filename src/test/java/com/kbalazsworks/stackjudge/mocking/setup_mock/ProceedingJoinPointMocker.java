package com.kbalazsworks.stackjudge.mocking.setup_mock;

import com.kbalazsworks.stackjudge.mocking.MockCreator;
import com.kbalazsworks.stackjudge.mocking.dummies.SignatureDummy;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;

import static org.mockito.Mockito.when;

public class ProceedingJoinPointMocker
{
    @SneakyThrows
    public static ProceedingJoinPoint proceed_returns_object(Object thenReturn)
    {
        ProceedingJoinPoint mock = MockCreator.getProceedingJoinPointMock();
        when(mock.proceed()).thenReturn(thenReturn);

        return mock;
    }

    public static void getSignature_returns_signatureDummy(
        ProceedingJoinPoint proceedingJoinPointMock
    )
    {
        when(proceedingJoinPointMock.getSignature()).thenReturn(new SignatureDummy());
    }
}
