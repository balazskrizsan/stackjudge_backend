package com.kbalazsworks.stackjudge.domain.aspect_services;

import org.aspectj.lang.annotation.Pointcut;

abstract public class AbstractAspectService
{
    @Pointcut("execution(* com.kbalazsworks.stackjudge.domain..*(..)) ")
    protected void findAllDomainClasses()
    {
    }

    @Pointcut("findAllDomainClasses() && inDomainServices() && inDomainRepositories() && inDomainRedisRepositories()")
    protected void findDomainBusinessLogicAndRepositoryClasses()
    {
    }

    @Pointcut("within(com.kbalazsworks.stackjudge.domain.services..*)")
    protected void inDomainServices()
    {
    }

    @Pointcut("!within(com.kbalazsworks.stackjudge.domain.repositories..*)")
    protected void inDomainRepositories()
    {
    }

    @Pointcut("!within(com.kbalazsworks.stackjudge.domain.redis_repositories..*)")
    protected void inDomainRedisRepositories()
    {
    }
}
