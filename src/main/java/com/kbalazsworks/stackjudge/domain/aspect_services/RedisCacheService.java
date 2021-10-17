package com.kbalazsworks.stackjudge.domain.aspect_services;

import com.google.common.collect.ImmutableList;
import com.kbalazsworks.stackjudge.domain.aspect_enums.RedisCacheRepositorieEnum;
import com.kbalazsworks.stackjudge.domain.aspects.RedisCacheByCompanyIdList;
import com.kbalazsworks.stackjudge.domain.entities.IRedisCacheable;
import com.kbalazsworks.stackjudge.domain.redis_repositories.AddressRedisRepository;
import com.kbalazsworks.stackjudge.domain.services.IRedisService;
import com.kbalazsworks.stackjudge.domain.services.address.AddressRedisService;
import com.kbalazsworks.stackjudge.domain.services.company.CompanyOwnersRedisService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class RedisCacheService
{
    private final AddressRedisRepository    addressRedisRepository;
    private final AddressRedisService       addressRedisService;
    private final CompanyOwnersRedisService companyOwnersRedisService;

    public Object cache(@NonNull ProceedingJoinPoint joinPont) throws Throwable
    {
        MethodSignature           signature  = (MethodSignature) joinPont.getSignature();
        Method                    method     = signature.getMethod();
        RedisCacheByCompanyIdList annotation = method.getAnnotation(RedisCacheByCompanyIdList.class);

        IRedisService<IRedisCacheable> genericRedisService
            = (IRedisService<IRedisCacheable>) getRedisService(annotation.repository());

        if (null == genericRedisService)
        {
            log.error("Missing repository: " + annotation.repository().toString());

            return joinPont.proceed();
        }

        Object[]     args                 = joinPont.getArgs();
        List<Long>   requestedIds         = (List<Long>) args[0];
        List<String> requestedIdsAsString = requestedIds.stream().map(Object::toString).collect(Collectors.toList());
        List<IRedisCacheable> cachedEntities = ImmutableList.copyOf(
            genericRedisService.findAllById(requestedIdsAsString)
        );
        List<Long> cachedIds  = cachedEntities.stream().map(IRedisCacheable::id).collect(Collectors.toList());
        List<Long> missingIds = new ArrayList<>(requestedIds);
        missingIds.removeAll(cachedIds);

        args[0] = missingIds;

        if (annotation.repository().getValue().equals(RedisCacheRepositorieEnum.ADDRESS.getValue()))
        {
            return listLogic(joinPont, genericRedisService, args, cachedEntities);
        }

        return mapLogic(joinPont, genericRedisService, args, cachedEntities);
    }

    private Map<Long, IRedisCacheable> mapLogic(
        @NonNull ProceedingJoinPoint joinPont,
        @NonNull IRedisService<IRedisCacheable> genericRedisService,
        Object[] args,
        @NonNull List<IRedisCacheable> cachedEntities
    ) throws Throwable
    {
        Map<Long, IRedisCacheable> sqlEntities = (Map<Long, IRedisCacheable>) joinPont.proceed(args);
        genericRedisService.saveAll(new ArrayList<>(sqlEntities.values()));
        sqlEntities.putAll(cachedEntities.stream().collect(Collectors.toMap(IRedisCacheable::id, Function.identity())));

        return sqlEntities;
    }

    private @NonNull List<IRedisCacheable> listLogic(
        @NonNull ProceedingJoinPoint joinPont,
        @NonNull IRedisService<IRedisCacheable> genericRedisService,
        Object[] args,
        @NonNull List<IRedisCacheable> cachedEntities
    ) throws Throwable
    {
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

        if (redisCacheRepositorieEnum.getValue().equals(RedisCacheRepositorieEnum.COMPANY_OWNER.getValue()))
        {
            return companyOwnersRedisService;
        }

        return null;
    }
}
