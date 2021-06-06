package com.kbalazsworks.stackjudge.unit.api.services.jwt_service;

import com.kbalazsworks.stackjudge.AbstractTest;
import com.kbalazsworks.stackjudge.MockFactory;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.api.services.jwt_service.JwtSubService;
import io.jsonwebtoken.JwtException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IsValidTest extends AbstractTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    @Test
    public void callWithValidToken_returnsTure()
    {
        // Arrange
        String testedRealTimeToken = serviceFactory.getJwtService().generateAccessToken(MockFactory.userMock);

        // Act
        boolean actualState = serviceFactory.getJwtService().isValid(testedRealTimeToken);

        // Assert
        assertThat(actualState).isTrue();
    }

    @Test
    public void callWithOldInvalidToken_returnsFalse()
    {
        // Arrange
        String testedToken = "token";

        JwtSubService jwtSubServiceMock = mock(JwtSubService.class);
        when(jwtSubServiceMock.errorHandledParseClaimsJws("token")).thenThrow(JwtException.class);

        // Act
        boolean actualState = serviceFactory
            .getJwtService(null, null, null, jwtSubServiceMock)
            .isValid(testedToken);

        // Assert
        assertThat(actualState).isFalse();
    }
}
