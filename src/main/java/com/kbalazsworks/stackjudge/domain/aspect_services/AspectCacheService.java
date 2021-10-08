package com.kbalazsworks.stackjudge.domain.aspect_services;

import com.google.common.collect.ImmutableList;
import com.kbalazsworks.stackjudge.domain.entities.Address;
import com.kbalazsworks.stackjudge.domain.redis_repositories.AddressRedisRepository;
import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
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
public class AspectCacheService
{
    private final AddressRedisRepository addressRedisRepository;
    private final ApplicationProperties  applicationProperties;

    @Around("execution(* com.kbalazsworks.stackjudge.domain..*(..)) && @annotation(RedisCachedCompanyIdList)")
    public Object caching(ProceedingJoinPoint joinPont) throws Throwable
    {
        if (!applicationProperties.getRedisSspectCacheEnabled())
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
        Object[] args = joinPont.getArgs();

        MethodSignature          signature                = (MethodSignature) joinPont.getSignature();
        Method                   method                   = signature.getMethod();
        RedisCachedCompanyIdList redisCachedCompanyIdList = method.getAnnotation(RedisCachedCompanyIdList.class);
//        Class<IRedisCacheable>   cacheableClass           = (Class<IRedisCacheable>) redisCachedCompanyIdList.entity();
//        System.out.println(redisCachedCompanyIdList.entity());

        List<Long>   requestedIds         = (List<Long>) args[0];
        List<String> requestedIdsAsString = requestedIds.stream().map(Object::toString).collect(Collectors.toList());

        List<Address> cachedAddresses = ImmutableList.copyOf(addressRedisRepository.findAllById(requestedIdsAsString));

        List<Long> cachedIds  = cachedAddresses.stream().map(Address::companyId).collect(Collectors.toList());
        List<Long> missingIds = new ArrayList<>(requestedIds);
        missingIds.removeAll(cachedIds);

        args[0] = missingIds;

        List<Address> sqlAddresses = (List<Address>) joinPont.proceed(args);

        addressRedisRepository.saveAll(sqlAddresses);

        sqlAddresses.addAll(cachedAddresses);

        return sqlAddresses;
    }
}
