package com.kbalazsworks.stackjudge.unit.api.services.jwt_service;

import com.kbalazsworks.stackjudge.AbstractTest;
import com.kbalazsworks.stackjudge.MockFactory;
import com.kbalazsworks.stackjudge.state.entities.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class JwtServiceGenerateAccessTokenTest extends AbstractTest
{
    @Autowired
    private MockFactory mockFactory;

    @Test
    public void createToken_returnsValidToken()
    {
        // Arrange
        User   testedUser    = MockFactory.userMock;
        String expectedToken = MockFactory.JWT_FOR_DEFAULT_TEST_METHOD;

        // Act
        String token = mockFactory
            .getMockedJwtService(null, null, null, null)
            .generateAccessToken(testedUser);

        // Assert
        assertThat(token).isEqualTo(expectedToken);
    }
}
