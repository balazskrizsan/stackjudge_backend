package com.kbalazsworks.stackjudge.unit.domain_aspects.aspects;

import com.kbalazsworks.stackjudge.AbstractTest;
import com.kbalazsworks.stackjudge.domain.address_module.repositories.AddressRedisRepository;
import com.kbalazsworks.stackjudge.domain.address_module.repositories.AddressRepository;
import com.kbalazsworks.stackjudge.domain.address_module.services.AddressService;
import com.kbalazsworks.stackjudge.domain.aws_module.services.CdnService;
import com.kbalazsworks.stackjudge.domain.common_module.factories.SystemFactory;
import com.kbalazsworks.stackjudge.domain_aspects.aspects.SlowServiceLoggerAspect;
import com.kbalazsworks.stackjudge.domain_aspects.services.SlowServiceLoggerAspectService;
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
    private AddressService addressService;

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
        // @todo3: looks like we have some false positive testcases, only cdnService false without matching aspect
        boolean[] actualProxyStatuses = {
            AopUtils.isAopProxy(cdnService),
            AopUtils.isAopProxy(addressRepository),
            AopUtils.isAopProxy(addressRedisRepository),
            AopUtils.isAopProxy(addressService)
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
