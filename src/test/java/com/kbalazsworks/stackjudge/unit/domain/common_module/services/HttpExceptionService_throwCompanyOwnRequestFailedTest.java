package com.kbalazsworks.stackjudge.unit.domain.common_module.services;

import com.kbalazsworks.stackjudge.AbstractTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class HttpExceptionService_throwCompanyOwnRequestFailedTest extends AbstractTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    @Test
    public void throwException_exceptionThrown()
    {
        assertThatThrownBy(() -> serviceFactory.getHttpExceptionService().throwCompanyOwnRequestFailed());
    }
}
