package com.kbalazsworks.stackjudge.integration.api.services.facebook_services;

import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.api.repositories.RegistrationSecretRepository;
import com.kbalazsworks.stackjudge.mocking.MockCreator;
import com.kbalazsworks.stackjudge.mocking.setup_mock.SecureRandomServiceMocker;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class FacebookService_registerAndLoginAndRedirectTest extends AbstractIntegrationTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    @Autowired
    private RegistrationSecretRepository registrationSecretRepository;

    @Test
    @SneakyThrows
    public void notExistingState_returnsLoginErrorUrl()
    {
        // Arrange
        String testedState         = "tested state";
        String testedCode          = "tested code";
        String expectedRedirectUrl = "http://stackjudge.com/account/login-error";

        // Act
        String actual = serviceFactory.getFacebookService(
            SecureRandomServiceMocker.getUrlEncoded_returns_string(32, testedState),
            null,
            null,
            serviceFactory.getRegistrationAndLoginService(),
            null,
            null,
            null
        ).registerAndLoginAndRedirect(MockCreator.getHttpServletResponseMock(), testedCode, testedState);

        // Assert
        assertThat(actual).isEqualTo(expectedRedirectUrl);
    }

//    @Test
//    public void existingState_returnsLoginAndRemovesStateFromRedis()
//    {
//
//    }
}
