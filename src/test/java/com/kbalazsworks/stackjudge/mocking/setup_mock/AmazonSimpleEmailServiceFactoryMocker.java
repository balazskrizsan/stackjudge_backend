package com.kbalazsworks.stackjudge.mocking.setup_mock;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.kbalazsworks.stackjudge.domain.aws_module.factories.AmazonSimpleEmailServiceFactory;
import com.kbalazsworks.stackjudge.mocking.MockCreator;

import static org.mockito.Mockito.when;

public class AmazonSimpleEmailServiceFactoryMocker
{
    public static AmazonSimpleEmailServiceFactory create_returns_mock(AmazonSimpleEmailService thanMock)
    {
        AmazonSimpleEmailServiceFactory mock = MockCreator.getAmazonSimpleEmailServiceFactory();
        when(mock.create()).thenReturn(thanMock);

        return mock;
    }
}
