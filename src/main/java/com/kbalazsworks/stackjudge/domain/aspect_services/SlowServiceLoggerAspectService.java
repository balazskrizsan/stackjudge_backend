package com.kbalazsworks.stackjudge.domain.aspect_services;

import com.kbalazsworks.stackjudge.domain.factories.SystemFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class SlowServiceLoggerAspectService
{
    private final SystemFactory systemFactory;

    private static final int SLOW_METHOD_RUN_LENGTH          = 1000;
    private static final int CRITICAL_SLOW_METHOD_RUN_LENGTH = 5000;

    public Object checkRunTime(ProceedingJoinPoint joinPont) throws Throwable
    {
        long   startTime = systemFactory.getCurrentTimeMillis();
        Object proceed   = joinPont.proceed();
        long   endTime   = systemFactory.getCurrentTimeMillis();
        long   diff      = endTime - startTime;

        if (diff > SLOW_METHOD_RUN_LENGTH)
        {
            writeLog(diff, joinPont.getSignature().toString());
        }

        return proceed;
    }

    private void writeLog(long diff, String slowMethod)
    {
        if (diff > CRITICAL_SLOW_METHOD_RUN_LENGTH)
        {
            log.error(String.format("Critical slow method run: %dms | %s", diff, slowMethod));

            return;
        }

        if (diff > SLOW_METHOD_RUN_LENGTH)
        {
            log.warn(String.format("Slow method run: %dms | %s", diff, slowMethod));
        }
    }
}
