package com.kbalazsworks.stackjudge.mocking.setup_mock;

import com.kbalazsworks.stackjudge.api.services.JwtService;
import com.kbalazsworks.stackjudge.fake_builders.UserFakeBuilder;
import com.kbalazsworks.stackjudge.mocking.MockCreator;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;

public class JwtServiceMocker
{
    public static JwtService generateAccessToken_returns_string(String thanAccessToken) throws Exception
    {
        JwtService jwtServiceMock = MockCreator.getJwtService();
        when(jwtServiceMock.generateAccessToken(argThat(u -> u.getId().equals(UserFakeBuilder.defaultId1))))
            .thenReturn(thanAccessToken);

        return jwtServiceMock;
    }
}
