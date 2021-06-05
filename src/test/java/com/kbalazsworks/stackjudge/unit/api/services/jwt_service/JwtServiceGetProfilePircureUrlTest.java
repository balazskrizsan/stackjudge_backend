package com.kbalazsworks.stackjudge.unit.api.services.jwt_service;

import com.kbalazsworks.stackjudge.AbstractTest;
import com.kbalazsworks.stackjudge.MockFactory;
import com.kbalazsworks.stackjudge.api.services.jwt_service.JwtSubService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JwtServiceGetProfilePircureUrlTest extends AbstractTest
{
    @Autowired
    private MockFactory mockFactory;

    @Test
    public void getProfilePictureUrlFromValidToken_perfect()
    {
        // Arrange
        int           profilePictureUrlIndex = 2;
        String        testedToken            = MockFactory.JWT_FOR_DEFAULT_TEST_METHOD;
        JwtSubService jwtSubServiceMock      = mock(JwtSubService.class);
        when(jwtSubServiceMock.getUserDataFormJwtString(testedToken, profilePictureUrlIndex))
            .thenReturn("http://logo.com/1.jpg");

        String expectedProfilePictureUrl = "http://logo.com/1.jpg";

        // Act
        String actualProfilePictureUrl = mockFactory
            .getMockedJwtService(null, null, null, jwtSubServiceMock)
            .getProfilePictureUrl(testedToken);

        // Assert
        assertThat(actualProfilePictureUrl).isEqualTo(expectedProfilePictureUrl);
    }
}
