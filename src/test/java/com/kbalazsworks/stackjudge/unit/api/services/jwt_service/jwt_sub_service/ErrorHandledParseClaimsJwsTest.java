package com.kbalazsworks.stackjudge.unit.api.services.jwt_service.jwt_sub_service;

import com.kbalazsworks.stackjudge.AbstractTest;
import com.kbalazsworks.stackjudge.MockFactory;
import com.kbalazsworks.stackjudge.ServiceFactory;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.impl.DefaultJws;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class ErrorHandledParseClaimsJwsTest extends AbstractTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    @Test
    public void callWithValidToken_returnsTokenClaims()
    {
        // Arrange
        String testedRealTimeToken = serviceFactory.getJwtService().generateAccessToken(MockFactory.userMock);

        Class<DefaultJws> expectedClass = DefaultJws.class;

        // Act
        Jws<Claims> actualClaims = serviceFactory.getJwtSubService().errorHandledParseClaimsJws(testedRealTimeToken);

        // Assert
        assertThat(actualClaims).isInstanceOf(expectedClass);
    }
}
