package com.kbalazsworks.stackjudge.unit.api.services.jwt_service;

import com.kbalazsworks.stackjudge.AbstractTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.fake_builders.UserFakeBuilder;
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
        User   testedUser       = new UserFakeBuilder().build();
        String expectedUserName = UserFakeBuilder.defaultUsername;

        // Act
        String token = serviceFactory.getJwtService().generateAccessToken(testedUser);

        // Act
        String actualUsername = serviceFactory.getJwtService().getUsername(token);

        // Assert
        assertThat(actualUsername).isEqualTo(expectedUserName);
    }
}
