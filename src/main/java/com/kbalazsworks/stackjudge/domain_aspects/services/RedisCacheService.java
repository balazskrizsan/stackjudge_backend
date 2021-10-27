package com.kbalazsworks.stackjudge.domain_aspects.services;

import com.kbalazsworks.stackjudge.domain_aspects.enums.RedisCacheRepositorieEnum;
import com.kbalazsworks.stackjudge.domain_aspects.aspects.RedisCacheByCompanyIdList;
import com.kbalazsworks.stackjudge.domain.common_module.entities.IRedisCacheable;
import com.kbalazsworks.stackjudge.domain.common_module.services.IRedisService;
import com.kbalazsworks.stackjudge.domain.address_module.services.AddressRedisService;
import com.kbalazsworks.stackjudge.domain.company_module.services.CompanyOwnersRedisService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class RedisCacheService
{
    private final AddressRedisService       addressRedisService;
    private final CompanyOwnersRedisService companyOwnersRedisService;

    public Object cache(@NonNull ProceedingJoinPoint joinPont) throws Throwable
    {
        Object[] args = joinPont.getArgs();

        List<Long> requestedIds = validateArgsAngGetRequestedIds(args);

        MethodSignature           signature  = (MethodSignature) joinPont.getSignature();
        Method                    method     = signature.getMethod();
        RedisCacheByCompanyIdList annotation = method.getAnnotation(RedisCacheByCompanyIdList.class);

        IRedisService<IRedisCacheable> genericRedisService = (IRedisService<IRedisCacheable>) getGenericRedisService(
            annotation.repository()
        );

        List<IRedisCacheable> cachedEntities = genericRedisService.findAllById(requestedIds);
        List<Long>            missingIds     = getMissingIds(cachedEntities, requestedIds);

        Map<Long, IRedisCacheable> sqlEntities = new HashMap<>();
        if (!missingIds.isEmpty())
        {
            args[0] = missingIds;

            sqlEntities = callOriginal(joinPont, genericRedisService, args, cachedEntities);
        }

        if (sqlEntities.size() > 0)
        {
            genericRedisService.saveAll(new ArrayList<>(sqlEntities.values()));
        }

        if (cachedEntities.size() > 0)
        {
            sqlEntities.putAll(
                cachedEntities.stream().collect(Collectors.toMap(IRedisCacheable::redisCacheId, Function.identity()))
            );
        }
        // @todo3: if (args[0].size > 0 ) { reorder result map with original id order }

        return sqlEntities;
    }

    private @NonNull List<Long> getMissingIds(
        @NonNull List<IRedisCacheable> cachedEntities,
        @NonNull List<Long> requestedIds
    )
    {
        List<Long> cachedIds = cachedEntities.stream().map(IRedisCacheable::redisCacheId).collect(Collectors.toList());

        List<Long> missingIds = new ArrayList<>(requestedIds);
        missingIds.removeAll(cachedIds);

        return missingIds;
    }

    private IRedisService<?> getGenericRedisService(@NonNull RedisCacheRepositorieEnum redisCacheRepositorieEnum)
    throws Exception
    {
        if (redisCacheRepositorieEnum.getValue().equals(RedisCacheRepositorieEnum.ADDRESS.getValue()))
        {
            return addressRedisService;
        }

        if (redisCacheRepositorieEnum.getValue().equals(RedisCacheRepositorieEnum.COMPANY_OWNER.getValue()))
        {
            return companyOwnersRedisService;
        }

        throw new Exception();
    }

    private @NonNull Map<Long, IRedisCacheable> callOriginal( /// COMPANY OWNERS
        @NonNull ProceedingJoinPoint joinPont,
        @NonNull IRedisService<IRedisCacheable> genericRedisService,
        Object[] args,
        @NonNull List<IRedisCacheable> cachedEntities
    ) throws Throwable
    {
        Map<Long, IRedisCacheable> sqlEntities = (Map<Long, IRedisCacheable>) joinPont.proceed(args);

        return sqlEntities;
    }

    private @NonNull List<Long> validateArgsAngGetRequestedIds(Object[] args) throws Exception
    {
        Object arg0 = args[0];
        if (!((arg0 instanceof List<?> arg0List)))
        {
            throw new Exception();
        }

        if (arg0List.isEmpty())
        {
            throw new Exception();
        }

        if (!(arg0List.get(0) instanceof Long))
        {
            throw new Exception();
        }

        return (List<Long>) arg0List;
    }
}
