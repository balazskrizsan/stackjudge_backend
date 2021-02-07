package com.kbalazsworks.stackjudge;

import com.kbalazsworks.stackjudge.domain.factories.DateFactory;
import com.kbalazsworks.stackjudge.domain.factories.LocalDateTimeFactory;
import com.kbalazsworks.stackjudge.session.entities.SessionState;
import com.kbalazsworks.stackjudge.session.entities.User;
import com.kbalazsworks.stackjudge.session.services.SessionService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockFactory
{
    public static final LocalDateTime localDateTimeMock = LocalDateTime.of(2011, 1, 2, 3, 4, 5);
    public static final User          userMock          = new User(123L, "MockUser Name", "Mock Password");
    public static final SessionState  sessionStateMock  = new SessionState(localDateTimeMock, userMock);

    public static Date getJavaDateFromDate(String date) throws ParseException
    {
        return new SimpleDateFormat("yyyy-MM-dd").parse(date);
    }

    public static Date getJavaDateFromDateTime(String dateTime) throws ParseException
    {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTime);
    }

    public static LocalDateTimeFactory getLocalDateTimeFactoryMockWithDateTime(String dateTime) throws ParseException
    {
        DateFactory dateFactory = mock(DateFactory.class);
        when(dateFactory.create()).thenReturn(getJavaDateFromDateTime(dateTime));

        LocalDateTimeFactory localDateTimeFactory = new LocalDateTimeFactory();
        localDateTimeFactory.setDateFactory(dateFactory);

        return localDateTimeFactory;
    }

    public static void SessionService_getSessionStateMock(SessionService sessionService)
    {
        when(sessionService.getSessionState()).thenReturn(sessionStateMock);
    }
}
