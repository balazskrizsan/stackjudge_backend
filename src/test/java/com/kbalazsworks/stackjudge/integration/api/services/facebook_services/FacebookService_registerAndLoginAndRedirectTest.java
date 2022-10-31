//package com.kbalazsworks.stackjudge.integration.api.services.facebook_services;
//
//import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
//import com.kbalazsworks.stackjudge.ServiceFactory;
//import com.kbalazsworks.stackjudge.api.services.RegistrationStateService;
//import com.kbalazsworks.stackjudge.api.services.SpringCookieService;
//import com.kbalazsworks.stackjudge.api.services.facebook_services.RegistrationAndLoginService;
//import com.kbalazsworks.stackjudge.fake_builders.UserFakeBuilder;
//import com.kbalazsworks.stackjudge.mocking.MockCreator;
//import com.kbalazsworks.stackjudge.mocking.setup_mock.JwtServiceMocker;
//import com.kbalazsworks.stackjudge.mocking.setup_mock.SecureRandomServiceMocker;
//import lombok.SneakyThrows;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import javax.servlet.http.Cookie;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertAll;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.argThat;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//public class FacebookService_registerAndLoginAndRedirectTest extends AbstractIntegrationTest
//{
//    @Autowired
//    private ServiceFactory serviceFactory;
//
//    @Autowired
//    private RegistrationStateService registrationStateService;
//
//    @Test
//    @SneakyThrows
//    public void notExistingState_returnsLoginErrorUrl()
//    {
//        // Arrange
//        //@todo: move to AOP annotation
//        getRedisConnection().flushAll();
//        String testedState         = "tested state";
//        String testedCode          = "tested code";
//        String expectedRedirectUrl = "http://stackjudge.com/account/login-error";
//
//        // Act
//        String actual = serviceFactory.getFacebookService(
//            SecureRandomServiceMocker.getUrlEncoded_returns_string(32, testedState),
//            null,
//            null,
//            serviceFactory.getRegistrationAndLoginService(),
//            null,
//            null,
//            null
//        ).registerAndLoginAndRedirect(MockCreator.getHttpServletResponseMock(), testedCode, testedState);
//
//        // Assert
//        assertThat(actual).isEqualTo(expectedRedirectUrl);
//    }
//
//    @Test
//    @SneakyThrows
//    public void existingState_returnsLoginUrlAndRemovesStateFromRedisAndSetLoginCookieOnHttpResponse()
//    {
//        // Arrange
//        //@todo: move to AOP annotation
//        getRedisConnection().flushAll();
//        registrationStateService.add("test-state", 1);
//
//        String testedCode  = "tested-code";
//        String testedState = "test-state";
//
//        String mockedCode      = "tested-code";
//        String mockAccessToken = "mock-access-token";
//        String mockedLoginUrl  = "mocked-login-url";
//
//        String expectedLoginUrl = "mocked-login-url";
//        Cookie expectedCookie   = new Cookie("Authorization", "Bearer_mock-access-token");
//        expectedCookie.setPath("/");
//        expectedCookie.setHttpOnly(true);
//        expectedCookie.setSecure(true);
//        expectedCookie.setMaxAge(60 * 60 * 24 * 7);
//
//        RegistrationAndLoginService registrationAndLoginServiceMock = MockCreator.getRegistrationAndLoginService();
//        when(registrationAndLoginServiceMock.updateOrSaveUser(mockedCode)).thenReturn(new UserFakeBuilder().build());
//        when(registrationAndLoginServiceMock.generateLoginUrl()).thenReturn(mockedLoginUrl);
//
//        SpringCookieService springCookieServiceMock = MockCreator.getSpringCookieService();
//
//        // Act
//        String actual = serviceFactory
//            .getFacebookService(
//                null,
//                null,
//                null,
//                registrationAndLoginServiceMock,
//                null,
//                JwtServiceMocker.generateAccessToken_returns_string(mockAccessToken),
//                springCookieServiceMock
//            )
//            .registerAndLoginAndRedirect(
//                MockCreator.getHttpServletResponseMock(),
//                testedCode,
//                testedState
//            );
//
//        // Assert
//        assertAll(
//            () -> assertThat(registrationStateService.exists("test-state")).isFalse(),
//            () -> assertThat(actual).isEqualTo(expectedLoginUrl),
//            () -> verify(springCookieServiceMock).set(
//                any(),
//                argThat(c -> c.getName().equals(expectedCookie.getName())
//                    && c.getValue().equals(expectedCookie.getValue())
//                    && c.getMaxAge() == expectedCookie.getMaxAge()
//                    && c.isHttpOnly() == expectedCookie.isHttpOnly()
//                    && c.getSecure() == expectedCookie.isHttpOnly()
//                    && c.getPath().equals(expectedCookie.getPath())
//                )
//            )
//        );
//        //@todo: move to AOP annotation
//        getRedisConnection().flushAll();
//    }
//}
