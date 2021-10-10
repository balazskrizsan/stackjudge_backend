package com.kbalazsworks.stackjudge.domain.aspects;

import com.kbalazsworks.stackjudge.domain.aspect_services.SlowServiceLoggerAspectService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class SlowServiceLoggerAspect extends AbstractAspectService
{
    private final SlowServiceLoggerAspectService slowServiceLoggerAspectService;

    @Around("findDomainBusinessLogicAndRepositoryClasses()")
    public Object checkRunTime(ProceedingJoinPoint joinPont) throws Throwable
    {
        return slowServiceLoggerAspectService.checkRunTime(joinPont);
    }
}
