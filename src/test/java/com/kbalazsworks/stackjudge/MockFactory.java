package com.kbalazsworks.stackjudge;

import com.kbalazsworks.stackjudge.domain.factories.DateFactory;
import com.kbalazsworks.stackjudge.domain.factories.LocalDateTimeFactory;
import com.kbalazsworks.stackjudge.state.entities.State;
import com.kbalazsworks.stackjudge.state.entities.User;
import com.kbalazsworks.stackjudge.state.services.StateService;
import lombok.SneakyThrows;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockFactory
{
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
    public static final String        USER_JWT_EXP_20210109_112233
                                                        = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMjMsTW9ja1VzZXIgTmFtZSxodHRwOi8vbG9nby5jb20vMS5qcGciLCJpc3MiOiJkZXYuc3RhY2tqdWRnZS5jb20iLCJpYXQiOjE2MDk1ODI5NTMsImV4cCI6MTYxMDE4Nzc1M30.XEG9ojmJnDPQmUVrjSkMKAFArzWBVrCj_OXckGxXTmn3ARW5xFCS8KOaahFlJNYUAxn45JWV7UHfpafcZaKt8g";

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
