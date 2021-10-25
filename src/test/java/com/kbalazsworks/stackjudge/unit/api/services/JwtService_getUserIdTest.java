package com.kbalazsworks.stackjudge.unit.api.services;

import com.kbalazsworks.stackjudge.AbstractTest;
import com.kbalazsworks.stackjudge.MockFactory;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.mocking.setup_mock.JwtSubServiceMocker;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class JwtService_getUserIdTest extends AbstractTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    @Test
    public void getUserIdFromValidToken_perfect()
    {
        // Arrange
        int    userIdIndex      = 0;
        String testedToken      = MockFactory.JWT_FOR_USER_FAKE_BUILDER;
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
