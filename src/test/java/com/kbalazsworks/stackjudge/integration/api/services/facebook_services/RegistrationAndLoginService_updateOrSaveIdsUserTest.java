//package com.kbalazsworks.stackjudge.integration.api.services.facebook_services;
//
//import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
//import com.kbalazsworks.stackjudge.ServiceFactory;
//import com.kbalazsworks.stackjudge.api.value_objects.FacebookUserWithAccessToken;
//import com.kbalazsworks.stackjudge.fake_builders.FacebookUserFakeBuilder;
//import com.kbalazsworks.stackjudge.fake_builders.UserFakeBuilder;
//import com.kbalazsworks.stackjudge.integration.annotations.TruncateAllTables;
//import com.kbalazsworks.stackjudge.mocking.setup_mock.OAuth2AccessTokenMocker;
//import com.kbalazsworks.stackjudge.mocking.setup_mock.ScribeJavaFacebookServiceMocker;
//import com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.ids._entities.IdsUser;
//import lombok.SneakyThrows;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//public class RegistrationAndLoginService_updateOrSaveIdsUserTest extends AbstractIntegrationTest
//{
//    @Autowired
//    private ServiceFactory serviceFactory;
//
//    @Test
//    @TruncateAllTables
//    @SneakyThrows
//    public void notExistingUserInDb_createsNewUserInDb()
//    {
//        // Arrange
//        String testedCode = "test-code";
//
//        String mockedCode = "test-code";
//        FacebookUserWithAccessToken mockedFacebookUserWithAccessToken = new FacebookUserWithAccessToken(
//            new FacebookUserFakeBuilder().build(),
//            OAuth2AccessTokenMocker.getAccessToken_returns_string("mocked-access-token")
//        );
//
//        IdsUser expectedIdsUser = new UserFakeBuilder().build();
//
//        // Act
//        serviceFactory.getRegistrationAndLoginService(
//            null,
//            ScribeJavaFacebookServiceMocker.getFacebookUserWithAccessTokenFromCode_returns_FacebookUserWithAccessToken(
//                mockedCode,
//                mockedFacebookUserWithAccessToken
//            ),
//            null,
//            null,
//            null
//        ).updateOrSaveUser(testedCode);
//
//        // Assert
//        IdsUser actualIdsUser = getQueryBuilder().selectFrom(usersTable).fetchOneInto(IdsUser.class);
//
//
//        System.out.println(actualIdsUser);
//        assertThat(actualIdsUser).isEqualTo(expectedIdsUser);
//
//    }
//}
