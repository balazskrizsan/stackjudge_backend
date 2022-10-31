//package com.kbalazsworks.stackjudge.integration.api.services.facebook_services;
//
//import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
//import com.kbalazsworks.stackjudge.ServiceFactory;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//public class RegistrationAndLoginService_generateLoginUrlTest extends AbstractIntegrationTest
//{
//    @Autowired
//    private ServiceFactory serviceFactory;
//
//    @Test
//    public void requestTheLoginUrl_returnsAsString()
//    {
//        // Arrange
//        String expectedUrl = "http://stackjudge.com/account/login";
//
//        // Act
//        String actualUrl = serviceFactory.getRegistrationAndLoginService().generateLoginUrl();
//
//        // Assert
//        assertThat(actualUrl).isEqualTo(expectedUrl);
//    }
//}
