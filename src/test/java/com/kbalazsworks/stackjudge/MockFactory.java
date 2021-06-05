package com.kbalazsworks.stackjudge;

import com.kbalazsworks.stackjudge.api.services.JwtService;
import com.kbalazsworks.stackjudge.api.services.jwt_service.JwtSubService;
import com.kbalazsworks.stackjudge.domain.factories.DateFactory;
import com.kbalazsworks.stackjudge.domain.factories.LocalDateTimeFactory;
import com.kbalazsworks.stackjudge.domain.factories.SystemFactory;
import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import com.kbalazsworks.stackjudge.state.entities.State;
import com.kbalazsworks.stackjudge.state.entities.User;
import com.kbalazsworks.stackjudge.state.services.StateService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Component
@RequiredArgsConstructor
public class MockFactory
{
    private final ServiceFactory serviceFactory;

    public static final LocalDateTime localDateTimeMock = LocalDateTime.of(2011, 1, 2, 3, 4, 5);
    public static final User          userMock          = new User(
        123L,
        true,
        false,
        "http://logo.com/1.jpg",
        "MockUser Name",
        "Mock Password",
        "fn_token",
        123L
    );
    public static final State         STATE_MOCK        = new State(localDateTimeMock, userMock);
    /***
     * {
     *   "alg": "HS512"
     * }
     * {
     *   "sub": "123,MockUser Name,http://logo.com/1.jpg",
     *   "iss": "dev.stackjudge.com",
     *   "iat": 1609582953,
     *   "exp": 1610187753
     * }
     * test jwt secret: 12345678901234567890123456789012
     */
    public static final String        JWT_FOR_DEFAULT_TEST_METHOD
                                                        = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjMsTW9ja1VzZXIgTmFtZSxodHRwOi8vbG9nby5jb20vMS5qcGciLCJpc3MiOiJkZXYuc3RhY2tqdWRnZS5jb20iLCJpYXQiOjE2MDk1ODI5NTMsImV4cCI6MTYxMDE4Nzc1M30.XEG9ojmJnDPQmUVrjSkMKAFArzWBVrCj_OXckGxXTmn3ARW5xFCS8KOaahFlJNYUAxn45JWV7UHfpafcZaKt8g";

    /*******************************************************************************************************************
     *
     *
     *         BEAN RELATED LOGIC
     *
     *
     */

    public JwtService getMockedJwtService(
        ApplicationProperties applicationPropertiesMock,
        DateFactory dateFactoryMock,
        SystemFactory systemFactoryMock,
        JwtSubService jwtSubServiceMock
    )
    {
        if (null == applicationPropertiesMock)
        {
            applicationPropertiesMock = mock(ApplicationProperties.class);
            when(applicationPropertiesMock.getJwtSecret()).thenReturn("12345678901234567890123456789012");
            when(applicationPropertiesMock.getSiteDomain()).thenReturn("dev.stackjudge.com");
        }

        if (null == dateFactoryMock)
        {
            String testedTime            = "2021-01-02 11:22:33";
            String testedTimePlusOneWeek = "2021-01-09 11:22:33";
            dateFactoryMock = mock(DateFactory.class);
            when(dateFactoryMock.create()).thenReturn(MockFactory.getJavaDateFromDateTime(testedTime));
            when(dateFactoryMock.create(anyLong()))
                .thenReturn(MockFactory.getJavaDateFromDateTime(testedTimePlusOneWeek));
        }

        return serviceFactory
            .getJwtService(applicationPropertiesMock, dateFactoryMock, systemFactoryMock, jwtSubServiceMock);
    }

    /*******************************************************************************************************************
     *
     *
     *         STATIC LOGIC
     *
     */

    public static Date getJavaDateFromDate(String date) throws ParseException
    {
        return new SimpleDateFormat("yyyy-MM-dd").parse(date);
    }

    @SneakyThrows
    public static Date getJavaDateFromDateTime(String dateTime)
    {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTime);
    }

    public static LocalDateTimeFactory getLocalDateTimeFactoryMockWithDateTime(String dateTime)
    {
        DateFactory dateFactory = mock(DateFactory.class);
        when(dateFactory.create()).thenReturn(getJavaDateFromDateTime(dateTime));

        LocalDateTimeFactory localDateTimeFactory = new LocalDateTimeFactory();
        localDateTimeFactory.setDateFactory(dateFactory);

        return localDateTimeFactory;
    }

    public static void SessionService_getStateMock(StateService stateService)
    {
        when(stateService.getState()).thenReturn(STATE_MOCK);
    }
}
