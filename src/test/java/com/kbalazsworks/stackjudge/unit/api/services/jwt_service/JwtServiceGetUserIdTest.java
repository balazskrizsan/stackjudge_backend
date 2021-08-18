package com.kbalazsworks.stackjudge.unit.api.services.jwt_service;

import com.kbalazsworks.stackjudge.AbstractTest;
import com.kbalazsworks.stackjudge.MockFactory;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.mocking.setup_mock.JwtSubServiceMocker;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class JwtServiceGetUserIdTest extends AbstractTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    @Test
    public void getUserIdFromValidToken_perfect()
    {
        // Arrange
        int    userIdIndex      = 0;
        String testedToken      = MockFactory.JWT_FOR_DEFAULT_TEST_METHOD;
        String mockReturnUserId = "123";
        long   expectedUserId   = 123;

        // Act
        long actualUserId = serviceFactory.getJwtMockedService(
                null,
                null,
                null,
                JwtSubServiceMocker.getUserDataFormJwtString_returns_userId(testedToken, userIdIndex, mockReturnUserId)
            )
            .getUserId(testedToken);

        // Assert
        assertThat(actualUserId).isEqualTo(expectedUserId);
    }
}
