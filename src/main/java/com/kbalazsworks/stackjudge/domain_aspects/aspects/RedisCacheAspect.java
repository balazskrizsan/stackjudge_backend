package com.kbalazsworks.stackjudge.domain_aspects.aspects;

import com.kbalazsworks.stackjudge.domain_aspects.services.RedisCacheService;
import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
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
public class RedisCacheAspect extends AbstractAspectService
{
    private final ApplicationProperties applicationProperties;
    private final RedisCacheService     redisCacheService;

    @Around("findRedisCacheByCompanyIdList()")
    public Object caching(ProceedingJoinPoint joinPont) throws Throwable
    {
        if (!applicationProperties.getRedisAspectCacheEnabled())
        {
            return joinPont.proceed();
        }

        try
        {
            return redisCacheService.cache(joinPont);
        }
        catch (Exception e)
        {
            log.error(e.getMessage(), e);
        }

        return joinPont.proceed();
    }
}
