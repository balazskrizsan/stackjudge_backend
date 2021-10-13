package com.kbalazsworks.stackjudge.domain.aspects;

import com.google.common.collect.ImmutableList;
import com.kbalazsworks.stackjudge.domain.aspect_enums.RedisCacheRepositorieEnum;
import com.kbalazsworks.stackjudge.domain.entities.Address;
import com.kbalazsworks.stackjudge.domain.entities.IRedisCacheable;
import com.kbalazsworks.stackjudge.domain.redis_repositories.AddressRedisRepository;
import com.kbalazsworks.stackjudge.domain.services.AddressRedisService;
import com.kbalazsworks.stackjudge.domain.services.IRedisService;
import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Component
@Log4j2
@RequiredArgsConstructor
public class AspectCacheService extends AbstractAspectService
{
    private final AddressRedisRepository addressRedisRepository;
    private final ApplicationProperties  applicationProperties;
    private final AddressRedisService    addressRedisService;

    @Around("findRedisCacheByCompanyIdList()")
    public Object caching(ProceedingJoinPoint joinPont) throws Throwable
    {
        if (!applicationProperties.getRedisAspectCacheEnabled())
        {
            return joinPont.proceed();
        }

        try
        {
            return cacheLogic(joinPont);
        }
        catch (Exception e)
        {
            log.error(e.getMessage(), e);
        }

        return joinPont.proceed();
    }

    private Object cacheLogic(ProceedingJoinPoint joinPont) throws Throwable
    {
        MethodSignature           signature  = (MethodSignature) joinPont.getSignature();
        Method                    method     = signature.getMethod();
        RedisCacheByCompanyIdList annotation = method.getAnnotation(RedisCacheByCompanyIdList.class);

        IRedisService<IRedisCacheable> genericRedisService = (IRedisService<IRedisCacheable>)getRedisService(annotation.repository());

        if (null == genericRedisService)
        {
            log.error("Missing repository: " + annotation.repository().toString());

            return joinPont.proceed();
        }

        Object[] args = joinPont.getArgs();

        // @todo: type check needed
        List<Long>   requestedIds         = (List<Long>) args[0];
        List<String> requestedIdsAsString = requestedIds.stream().map(Object::toString).collect(Collectors.toList());

        List<Address> cachedEntities = ImmutableList.copyOf(genericRedisService.findAllById(requestedIdsAsString));

        List<Long> cachedIds  = cachedEntities.stream().map(Address::companyId).collect(Collectors.toList());
        List<Long> missingIds = new ArrayList<>(requestedIds);
        missingIds.removeAll(cachedIds);

        args[0] = missingIds;

        List<IRedisCacheable> sqlEntities = (List<IRedisCacheable>) joinPont.proceed(args);

        genericRedisService.saveAll(sqlEntities);

        sqlEntities.addAll(cachedEntities);

        return sqlEntities;
    }

    private IRedisService<?> getRedisService(@NonNull RedisCacheRepositorieEnum redisCacheRepositorieEnum)
    {
        if (redisCacheRepositorieEnum.getValue().equals(RedisCacheRepositorieEnum.ADDRESS.getValue()))
        {
            return addressRedisService;
        }

        return null;
    }
}
