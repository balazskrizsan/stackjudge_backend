package com.kbalazsworks.stackjudge.unit.api.services;

import com.kbalazsworks.stackjudge.AbstractTest;
import com.kbalazsworks.stackjudge.MockFactory;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.mocking.setup_mock.JwtSubServiceMocker;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class JwtService_getProfilePircureUrlTest extends AbstractTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    @Test
    public void getProfilePictureUrlFromValidToken_perfect()
    {
        // Arrange
        int    profilePictureUrlIndex    = 2;
        String testedToken               = MockFactory.JWT_FOR_USER_FAKE_BUILDER;
        String expectedProfilePictureUrl = "http://logo.com/1.jpg";

        // Act
        String actualProfilePictureUrl = serviceFactory.getJwtMockedService(
                null,
                null,
                null,
                JwtSubServiceMocker.getUserDataFormJwtString_returns_profilePictureUrl(
                    testedToken,
                    profilePictureUrlIndex
                )
            )
            .getProfilePictureUrl(testedToken);

        // Assert
        assertThat(actualProfilePictureUrl).isEqualTo(expectedProfilePictureUrl);
    }
}
