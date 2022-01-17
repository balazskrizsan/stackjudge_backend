package com.kbalazsworks.stackjudge.unit.api.services;

import com.kbalazsworks.stackjudge.AbstractTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.fake_builders.UserFakeBuilder;
import com.kbalazsworks.stackjudge.state.entities.User;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class JwtService_getUsernameTest extends AbstractTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    @Test
    @SneakyThrows
    public void getUsernameFromValidToken_perfect()
    {
        // Arrange
        User   testedUser       = new UserFakeBuilder().build();
        String expectedUserName = UserFakeBuilder.defaultUsername1;

        // Act
        String token = serviceFactory.getJwtService().generateAccessToken(testedUser);

        // Act
        String actualUsername = serviceFactory.getJwtService().getUsername(token);

        // Assert
        assertThat(actualUsername).isEqualTo(expectedUserName);
    }
}
