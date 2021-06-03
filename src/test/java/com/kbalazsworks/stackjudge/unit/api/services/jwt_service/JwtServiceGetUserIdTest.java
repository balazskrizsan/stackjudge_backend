package com.kbalazsworks.stackjudge.unit.api.services.jwt_service;

import com.kbalazsworks.stackjudge.AbstractTest;
import com.kbalazsworks.stackjudge.MockFactory;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import com.kbalazsworks.stackjudge.state.entities.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JwtServiceGetUserIdTest extends AbstractTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    @Test
    public void getUserIdFromValidToken_perfect()
    {
        // Arrange
        ApplicationProperties applicationPropertiesMock = mock(ApplicationProperties.class);
        when(applicationPropertiesMock.getJwtSecret()).thenReturn("12345678901234567890123456789012");
        when(applicationPropertiesMock.getSiteDomain()).thenReturn("dev.stackjudge.com");
        User testedUser = MockFactory.userMock;

        Long expectedUserId = 123L;

        // Act
        String token = serviceFactory
            .getJwtService(applicationPropertiesMock, null, null, null)
            .generateAccessToken(testedUser);

        // Act
        long actualUserId = serviceFactory.getJwtService().getUserId(token);

        // Assert
        assertThat(actualUserId).isEqualTo(expectedUserId);
    }
}
