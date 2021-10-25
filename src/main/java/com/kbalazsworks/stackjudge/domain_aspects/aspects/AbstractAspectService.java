package com.kbalazsworks.stackjudge.domain_aspects.aspects;

import org.aspectj.lang.annotation.Pointcut;

abstract public class AbstractAspectService
{
    @Pointcut("execution(* com.kbalazsworks.stackjudge.domain..*(..)) ")
    protected void findAllDomainClasses()
    {
    }

    @Pointcut("@annotation(RedisCacheByCompanyIdList) && findAllDomainClasses()")
    protected void findRedisCacheByCompanyIdList()
    {
    }

    @Pointcut("findAllDomainClasses() && inDomainServices() && inDomainRepositories() && inDomainRedisRepositories()")
    protected void findDomainBusinessLogicAndRepositoryClasses()
    {
    }

    @Pointcut("within(com.kbalazsworks.stackjudge.domain..*module.services..*)")
    protected void inDomainServices()
    {
    }

    @Pointcut("!within(com.kbalazsworks.stackjudge.domain..*module.repositories..*)")
    protected void inDomainRepositories()
    {
    }

    @Pointcut("!within(com.kbalazsworks.stackjudge.domain..*module.redis_repositories..*)")
    protected void inDomainRedisRepositories()
    {
    }
}
