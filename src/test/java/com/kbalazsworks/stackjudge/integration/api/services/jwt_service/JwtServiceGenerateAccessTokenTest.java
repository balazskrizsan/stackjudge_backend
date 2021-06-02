package com.kbalazsworks.stackjudge.integration.api.services.jwt_service;

import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.MockFactory;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.api.services.JwtService;
import com.kbalazsworks.stackjudge.domain.factories.DateFactory;
import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import com.kbalazsworks.stackjudge.state.entities.User;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JwtServiceGenerateAccessTokenTest extends AbstractIntegrationTest
{
    @Autowired
    private ServiceFactory serviceFactory;
    private JwtService     jwtService;

    @BeforeEach
    @AfterEach
    public void clean()
    {
        jwtService = serviceFactory.getJwtService();
    }


    @Test
    public void createToken_returnsValidToken()
    {
        // Arrange
        String      testedTime            = "2021-01-02 11:22:33";
        String      testedTimePlusOneWeek = "2021-01-09 11:22:33";
        DateFactory dateFactory           = mock(DateFactory.class);
        when(dateFactory.create()).thenReturn(MockFactory.getJavaDateFromDateTime(testedTime));
        when(dateFactory.create(anyLong())).thenReturn(MockFactory.getJavaDateFromDateTime(testedTimePlusOneWeek));

        ApplicationProperties applicationPropertiesMock = mock(ApplicationProperties.class);
        when(applicationPropertiesMock.getJwtSecret()).thenReturn("12345678901234567890123456789012");
        when(applicationPropertiesMock.getSiteDomain()).thenReturn("dev.stackjudge.com");
        jwtService = serviceFactory.getJwtService(applicationPropertiesMock, dateFactory, null);
        User testedUser = MockFactory.userMock;

        String expectedToken = MockFactory.USER_JWT_EXP_20210109_112233;

        // Act
        String token = jwtService.generateAccessToken(testedUser);

        // Assert
        assertThat(token).isEqualTo(expectedToken);
    }
}
