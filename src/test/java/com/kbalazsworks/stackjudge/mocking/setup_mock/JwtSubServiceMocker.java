//package com.kbalazsworks.stackjudge.mocking.setup_mock;
//
//import com.kbalazsworks.stackjudge.api.services.jwt_service.JwtSubService;
//import com.kbalazsworks.stackjudge.mocking.MockCreator;
//import io.jsonwebtoken.JwtException;
//
//import static org.mockito.Mockito.when;
//
//public class JwtSubServiceMocker extends MockCreator
//{
//    public static JwtSubService getUserDataFormJwtString_returns_profilePictureUrl(
//        String whenToken,
//        int thanPictureUrlIndex
//    )
//    {
//        JwtSubService mock = getJwtSubServiceMock();
//        when(mock.getUserDataFormJwtString(whenToken, thanPictureUrlIndex)).thenReturn("http://logo.com/1.jpg");
//
//        return mock;
//    }
//
//    public static JwtSubService errorHandledParseClaimsJws_throws_JwtException(String whenToken)
//    {
//        JwtSubService mock = getJwtSubServiceMock();
//        when(mock.errorHandledParseClaimsJws(whenToken)).thenThrow(JwtException.class);
//
//        return mock;
//    }
//
//    public static JwtSubService getUserDataFormJwtString_returns_userId(
//        String whenToken,
//        int whenUserIdIndex,
//        String thenUserId
//    )
//    {
//        JwtSubService mock = getJwtSubServiceMock();
//        when(mock.getUserDataFormJwtString(whenToken, whenUserIdIndex)).thenReturn(thenUserId);
//
//        return mock;
//    }
//}
