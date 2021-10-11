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
import static org.junit.jupiter.api.Assertions.assertAll;

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
        // Assert
        assertAll(
            () -> assertThat(AopUtils.isAopProxy(cdnService)).isTrue(),
            () -> assertThat(AopUtils.isAopProxy(addressRepository)).isTrue(),
            () -> assertThat(AopUtils.isAopProxy(addressRedisRepository)).isTrue()
        );
    }

    @Test
    public void checkProxyAssigned_mustNotHaveAopProxy()
    {
        // Arrange
        // Act
        // Assert
        assertAll(
            () -> assertThat(AopUtils.isAopProxy(slowServiceLoggerAspectService)).isFalse(),
            () -> assertThat(AopUtils.isAopProxy(systemFactory)).isFalse(),
            () -> assertThat(AopUtils.isAopProxy(systemFactory)).isFalse(),
            () -> assertThat(AopUtils.isAopProxy(slowServiceLoggerAspect)).isFalse()
        );
    }
}
