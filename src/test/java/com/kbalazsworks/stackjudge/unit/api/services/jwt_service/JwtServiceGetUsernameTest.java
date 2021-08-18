package com.kbalazsworks.stackjudge.unit.api.services.jwt_service;

import com.kbalazsworks.stackjudge.AbstractTest;
import com.kbalazsworks.stackjudge.MockFactory;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.mocking.MockCreator;
import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import com.kbalazsworks.stackjudge.state.entities.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class JwtServiceGetUsernameTest extends AbstractTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    @Test
    public void getUsernameFromValidToken_perfect()
    {
        // Arrange
        ApplicationProperties applicationPropertiesMock = MockCreator.getApplicationPropertiesMock();
        when(applicationPropertiesMock.getJwtSecret()).thenReturn("12345678901234567890123456789012");
        when(applicationPropertiesMock.getSiteDomain()).thenReturn("dev.stackjudge.com");
        User testedUser = MockFactory.userMock;

        String expectedUserId = "MockUser Name";

        // Act
        String token = serviceFactory
            .getJwtService(applicationPropertiesMock, null, null, null)
            .generateAccessToken(testedUser);

        // Act
        String actualUsername = serviceFactory.getJwtService().getUsername(token);

        // Assert
        assertThat(actualUsername).isEqualTo(expectedUserId);
    }
}
