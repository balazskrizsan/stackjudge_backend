package com.kbalazsworks.stackjudge.unit.api.services.jwt_service;

import com.kbalazsworks.stackjudge.AbstractTest;
import com.kbalazsworks.stackjudge.MockFactory;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.mocking.setup_mock.ApplicationPropertiesMocker;
import com.kbalazsworks.stackjudge.state.entities.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class JwtServiceGetUsernameTest extends AbstractTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    @Test
    public void getUsernameFromValidToken_perfect()
    {
        // Arrange
        User   testedUser     = MockFactory.userMock;
        String expectedUserId = "MockUser Name";

        // Act
        String token = serviceFactory.getJwtService(
            ApplicationPropertiesMocker.getDefaultMock(),
            null,
            null,
            null
        )
            .generateAccessToken(testedUser);

        // Act
        String actualUsername = serviceFactory.getJwtService().getUsername(token);

        // Assert
        assertThat(actualUsername).isEqualTo(expectedUserId);
    }
}
