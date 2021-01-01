package com.kbalazsworks.stackjudge;

import com.kbalazsworks.stackjudge.domain.factories.DateFactory;
import com.kbalazsworks.stackjudge.domain.factories.LocalDateTimeFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockFactory
{
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
}
