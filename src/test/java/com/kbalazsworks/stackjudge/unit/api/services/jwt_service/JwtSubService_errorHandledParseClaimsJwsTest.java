//package com.kbalazsworks.stackjudge.unit.api.services.jwt_service;
//
//import com.kbalazsworks.stackjudge.AbstractTest;
//import com.kbalazsworks.stackjudge.ServiceFactory;
//import com.kbalazsworks.stackjudge.api.factories.JwtFactory;
//import com.kbalazsworks.stackjudge.api.services.jwt_service.JwtSubService;
//import com.kbalazsworks.stackjudge.fake_builders.UserFakeBuilder;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.Jws;
//import io.jsonwebtoken.JwtException;
//import io.jsonwebtoken.MalformedJwtException;
//import io.jsonwebtoken.SignatureException;
//import io.jsonwebtoken.UnsupportedJwtException;
//import io.jsonwebtoken.impl.DefaultClaims;
//import io.jsonwebtoken.impl.DefaultHeader;
//import io.jsonwebtoken.impl.DefaultJws;
//import lombok.SneakyThrows;
//import nl.altindag.log.LogCaptor;
//import org.junit.Test;
//import org.junit.jupiter.api.RepeatedTest;
//import org.junit.jupiter.api.RepetitionInfo;
//import org.junit.platform.commons.JUnitException;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.assertThatThrownBy;
//import static org.junit.jupiter.api.Assertions.assertAll;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//public class JwtSubService_errorHandledParseClaimsJwsTest extends AbstractTest
//{
//    @Autowired
//    private ServiceFactory serviceFactory;
//
//    private record TestData(
//        Throwable testedException,
//        String expectedExceptionMessage,
//        Class<JwtException> expectedException,
//        String expectedLogMessage
//    )
//    {
//    }
//
//    private TestData providerFor_mockedCall_returnsWithExceptionAndLogError(int repetition)
//    {
//        if (1 == repetition)
//        {
//            return new TestData(
//                new SignatureException("AAA"),
//                "Invalid authentication error",
//                JwtException.class,
//                "Invalid JWT signature; AAA"
//            );
//        }
//        if (2 == repetition)
//        {
//            return new TestData(
//                new MalformedJwtException("BBB"),
//                "Invalid authentication error",
//                JwtException.class,
//                "Invalid JWT token; BBB"
//            );
//        }
//        if (3 == repetition)
//        {
//            return new TestData(
//                new ExpiredJwtException(new DefaultHeader<>(), new DefaultClaims(), ""),
//                "Invalid authentication error",
//                JwtException.class,
//                "Expired JWT token"
//            );
//        }
//        if (4 == repetition)
//        {
//            return new TestData(
//                new UnsupportedJwtException("DDD"),
//                "Invalid authentication error",
//                JwtException.class,
//                "Unsupported JWT token; DDD"
//            );
//        }
//        if (5 == repetition)
//        {
//            return new TestData(
//                new IllegalArgumentException("EEE"),
//                "Invalid authentication error",
//                JwtException.class,
//                "JWT claims string is empty; EEE"
//            );
//        }
//
//        throw new JUnitException("TestData not found with repetition#" + repetition);
//    }
//
////    @RepeatedTest(5)
////    public void mockedCall_returnsWithExceptionAndLogError(RepetitionInfo repetitionInfo)
////    {
////        // Arrange
////        LogCaptor logCaptor = LogCaptor.forClass(JwtSubService.class);
////
////        TestData testData = providerFor_mockedCall_returnsWithExceptionAndLogError(
////            repetitionInfo.getCurrentRepetition()
////        );
////        JwtFactory jwtFactoryMock = mock(JwtFactory.class);
////        when(jwtFactoryMock.createJwtParser()).thenThrow(testData.testedException());
////
////        // Act - Assert
////        assertAll(
////            () -> assertThatThrownBy(() -> serviceFactory
////                .getJwtSubService(null, jwtFactoryMock)
////                .errorHandledParseClaimsJws("fake_token"))
////                .isInstanceOf(testData.expectedException()).hasMessage(testData.expectedExceptionMessage()),
////            () -> assertThat(logCaptor.getErrorLogs()).containsExactly(testData.expectedLogMessage())
////        );
////    }
//
////    @Test
////    @SneakyThrows
////    public void callWithValidToken_returnsTokenClaims()
////    {
////        // Arrange
////        String testedRealTimeToken = serviceFactory.getOldJwtService().generateAccessToken(new UserFakeBuilder().build());
////
////        Class<DefaultJws> expectedClass = DefaultJws.class;
////
////        // Act
////        Jws<Claims> actualClaims = serviceFactory.getJwtSubService().errorHandledParseClaimsJws(testedRealTimeToken);
////
////        // Assert
////        assertThat(actualClaims).isInstanceOf(expectedClass);
////    }
//}
