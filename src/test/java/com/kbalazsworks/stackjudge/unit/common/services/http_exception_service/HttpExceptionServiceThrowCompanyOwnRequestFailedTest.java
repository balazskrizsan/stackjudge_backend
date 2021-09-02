package com.kbalazsworks.stackjudge.unit.common.services.http_exception_service;

import com.kbalazsworks.stackjudge.AbstractTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class HttpExceptionServiceThrowCompanyOwnRequestFailedTest extends AbstractTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    @Test
    public void throwException_exceptionThrown()
    {
        assertThatThrownBy(() -> serviceFactory.getHttpExceptionService().throwCompanyOwnRequestFailed());
    }
}
