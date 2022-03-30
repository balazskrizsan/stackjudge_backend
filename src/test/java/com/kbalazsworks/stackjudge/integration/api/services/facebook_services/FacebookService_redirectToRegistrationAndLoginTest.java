package com.kbalazsworks.stackjudge.integration.api.services.facebook_services;

import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.api.repositories.RegistrationSecretRepository;
import com.kbalazsworks.stackjudge.mocking.setup_mock.OAuthFacebookServiceBuilderMocker;
import com.kbalazsworks.stackjudge.mocking.setup_mock.SecureRandomServiceMocker;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class FacebookService_redirectToRegistrationAndLoginTest extends AbstractIntegrationTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    @Autowired
    private RegistrationSecretRepository registrationSecretRepository;

    @Test
    public void loginOrRegisterUrlGenerator_mocksCalledWithExpectedValue()
    {
        // Arrange
        String testedState         = "asdfgh";
        String mockedRedirectUri   = "http://test.com/blabla";
        String expectedRedirectUrl = "http://test.com/blabla";
        String expectedState       = "asdfgh";

        // Act
        String actual = serviceFactory.getFacebookService(
            SecureRandomServiceMocker.getUrlEncoded_returns_string(32, testedState),
            null,
            OAuthFacebookServiceBuilderMocker.createAuthorizationUrlBuilder_state_build_return_string(
                testedState,
                mockedRedirectUri
            ),
            null,
            null
        ).redirectToRegistrationAndLogin();

        // Assert
        assertAll(
            () -> assertThat(actual).isEqualTo(expectedRedirectUrl),
            () -> assertThat(registrationSecretRepository.existsById(expectedState)).isTrue()
        );
        //@todo: move to AOP annotation
        getRedisConnection().flushAll();
    }
}
