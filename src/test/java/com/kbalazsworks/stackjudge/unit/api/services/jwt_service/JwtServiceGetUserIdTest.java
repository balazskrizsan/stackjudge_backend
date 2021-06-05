package com.kbalazsworks.stackjudge.unit.api.services.jwt_service;

import com.kbalazsworks.stackjudge.AbstractTest;
import com.kbalazsworks.stackjudge.MockFactory;
import com.kbalazsworks.stackjudge.api.services.jwt_service.JwtSubService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JwtServiceGetUserIdTest extends AbstractTest
{
    @Autowired
    private MockFactory mockFactory;

    @Test
    public void getUserIdFromValidToken_perfect()
    {
        // Arrange
        int           userIdIndex       = 0;
        String        testedToken       = MockFactory.JWT_FOR_DEFAULT_TEST_METHOD;
        JwtSubService jwtSubServiceMock = mock(JwtSubService.class);
        when(jwtSubServiceMock.getUserDataFormJwtString(testedToken, userIdIndex)).thenReturn("123");

        long expectedUserId = 123;

        // Act
        long actualUserId = mockFactory
            .getMockedJwtService(null, null, null, jwtSubServiceMock)
            .getUserId(testedToken);

        // Assert
        assertThat(actualUserId).isEqualTo(expectedUserId);
    }
}
