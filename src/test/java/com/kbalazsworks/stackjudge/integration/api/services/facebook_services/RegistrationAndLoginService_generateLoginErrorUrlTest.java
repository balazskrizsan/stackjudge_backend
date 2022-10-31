//package com.kbalazsworks.stackjudge.integration.api.services.facebook_services;
//
//import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
//import com.kbalazsworks.stackjudge.ServiceFactory;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//public class RegistrationAndLoginService_generateLoginErrorUrlTest extends AbstractIntegrationTest
//{
//    @Autowired
//    private ServiceFactory serviceFactory;
//
//    @Test
//    public void requestTheLoginErrorUrl_returnsAsString()
//    {
//        // Arrange
//        String expectedErrorUrl = "http://stackjudge.com/account/login-error";
//
//        // Act
//        String actualErrorUrl = serviceFactory.getRegistrationAndLoginService().generateLoginErrorUrl();
//
//        // Assert
//        assertThat(actualErrorUrl).isEqualTo(expectedErrorUrl);
//    }
//}
