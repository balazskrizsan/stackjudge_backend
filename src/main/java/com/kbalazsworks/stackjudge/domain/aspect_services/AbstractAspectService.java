package com.kbalazsworks.stackjudge.domain.aspect_services;

import org.aspectj.lang.annotation.Pointcut;

abstract public class AbstractAspectService
{
    @Pointcut("execution(* com.kbalazsworks.stackjudge.domain..*(..)) ")
    protected void findAllDomainClasses()
    {
    }

    @Pointcut("findAllDomainClasses() && inServices() && inRepositories() && inRedisRepositories()")
    protected void findDomainBusinessLogicAndRepositoryClasses()
    {
    }

    @Pointcut("within(com.kbalazsworks.stackjudge.domain.services..*)")
    protected void inServices()
    {
    }

    @Pointcut("!within(com.kbalazsworks.stackjudge.domain.repositories..*)")
    protected void inRepositories()
    {
    }

    @Pointcut("!within(com.kbalazsworks.stackjudge.domain.redis_repositories..*)")
    protected void inRedisRepositories()
    {
    }
}
