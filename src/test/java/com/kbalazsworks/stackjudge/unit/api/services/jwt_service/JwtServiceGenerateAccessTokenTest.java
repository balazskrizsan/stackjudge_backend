package com.kbalazsworks.stackjudge.unit.api.services.jwt_service;

import com.kbalazsworks.stackjudge.AbstractTest;
import com.kbalazsworks.stackjudge.MockFactory;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.fake_builders.UserFakeBuilder;
import com.kbalazsworks.stackjudge.state.entities.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class JwtServiceGenerateAccessTokenTest extends AbstractTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    @Test
    public void createToken_returnsValidToken()
    {
        // Arrange
        User   testedUser    = new UserFakeBuilder().build();
        String expectedToken = MockFactory.JWT_FOR_USER_FAKE_BUILDER;

        // Act
        String token = serviceFactory.getJwtMockedService(
            null,
            null,
            null,
            null
        )
            .generateAccessToken(testedUser);

        // Assert
        assertThat(token).isEqualTo(expectedToken);
    }
}
