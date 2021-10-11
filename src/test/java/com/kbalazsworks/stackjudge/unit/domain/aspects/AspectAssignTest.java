package com.kbalazsworks.stackjudge.unit.domain.aspects;

import com.kbalazsworks.stackjudge.AbstractTest;
import com.kbalazsworks.stackjudge.domain.aspect_services.SlowServiceLoggerAspectService;
import com.kbalazsworks.stackjudge.domain.aspects.SlowServiceLoggerAspect;
import com.kbalazsworks.stackjudge.domain.factories.SystemFactory;
import com.kbalazsworks.stackjudge.domain.redis_repositories.AddressRedisRepository;
import com.kbalazsworks.stackjudge.domain.repositories.AddressRepository;
import com.kbalazsworks.stackjudge.domain.services.CdnService;
import org.junit.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class AspectAssignTest extends AbstractTest
{
    @Autowired
    private CdnService cdnService;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressRedisRepository addressRedisRepository;

    @Autowired
    private SlowServiceLoggerAspectService slowServiceLoggerAspectService;

    @Autowired
    private SystemFactory systemFactory;

    @Autowired
    private SlowServiceLoggerAspect slowServiceLoggerAspect;

    @Test
    public void checkProxyAssigned_mustHaveAopProxy()
    {
        // Arrange
        // Act
        boolean[] actualProxyStatuses = {
            AopUtils.isAopProxy(cdnService),
            AopUtils.isAopProxy(addressRepository),
            AopUtils.isAopProxy(addressRedisRepository)
        };

        // Assert
        assertThat(actualProxyStatuses).containsOnly(true);
    }

    @Test
    public void checkProxyAssigned_mustNotHaveAopProxy()
    {
        // Arrange
        // Act
        boolean[] actualProxyStatuses = {
            AopUtils.isAopProxy(slowServiceLoggerAspectService),
            AopUtils.isAopProxy(systemFactory),
            AopUtils.isAopProxy(systemFactory),
            AopUtils.isAopProxy(slowServiceLoggerAspect)
        };

        // Assert
        assertThat(actualProxyStatuses).containsOnly(false);
    }
}
