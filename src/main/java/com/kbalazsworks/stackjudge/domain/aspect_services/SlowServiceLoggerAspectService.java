package com.kbalazsworks.stackjudge.domain.aspect_services;

import com.kbalazsworks.stackjudge.domain.factories.SystemFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Log4j2
@RequiredArgsConstructor
public class SlowServiceLoggerAspectService extends AbstractAspectService
{
    private final SystemFactory systemFactory;

    private static final int SLOW_METHOD_RUN_LENGTH          = 1000;
    private static final int CRITICAL_SLOW_METHOD_RUN_LENGTH = 5000;

    @Around("findDomainBusinessLogicAndRepositoryClasses()")
    public Object checkRunTime(ProceedingJoinPoint joinPont) throws Throwable
    {
        long   startTime = systemFactory.getCurrentTimeMillis();
        Object retVal    = joinPont.proceed(joinPont.getArgs());
        long   endTime   = systemFactory.getCurrentTimeMillis();
        long   diff      = endTime - startTime;

        if (diff > SLOW_METHOD_RUN_LENGTH)
        {
            logging(diff, joinPont.getSignature().toString());
        }

        return retVal;
    }

    private void logging(long diff, String slowMethod)
    {
        if (diff > SLOW_METHOD_RUN_LENGTH)
        {
            log.warn(String.format("Slow method run: %dms | %s ", diff, slowMethod));
        }

        if (diff > CRITICAL_SLOW_METHOD_RUN_LENGTH)
        {
            log.error(String.format("Critical slow method run: %dms | %s ", diff, slowMethod));
        }
    }
}
