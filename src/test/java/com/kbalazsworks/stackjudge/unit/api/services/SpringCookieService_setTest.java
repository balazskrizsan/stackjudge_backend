package com.kbalazsworks.stackjudge.unit.api.services;

import com.kbalazsworks.stackjudge.AbstractTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.mocking.MockCreator;
import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

public class SpringCookieService_setTest extends AbstractTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    @Test
    public void vintageHack()
    {
        assertTrue(true);
    }

    private record TestData(Cookie testedCookie, String expectedCookieString)
    {
    }

    private TestData provider(int repetition)
    {
        if (1 == repetition)
        {
            return new TestData(new Cookie("q1", "v1"), "q1=v1;");
        }
        if (2 == repetition)
        {
            Cookie c = new Cookie("q1", "v1");
            c.setSecure(true);

            return new TestData(c, "q1=v1; Secure;");
        }
        if (3 == repetition)
        {
            Cookie c = new Cookie("q1", "v1");
            c.setSecure(true);
            c.setHttpOnly(true);

            return new TestData(c, "q1=v1; Secure; HttpOnly;");
        }
        if (4 == repetition)
        {
            Cookie c = new Cookie("q1", "v1");
            c.setHttpOnly(true);

            return new TestData(c, "q1=v1; HttpOnly;");
        }
        if (5 == repetition)
        {
            Cookie c = new Cookie("q1", "v1");
            c.setSecure(false);
            c.setHttpOnly(false);

            return new TestData(c, "q1=v1;");
        }
        if (6 == repetition)
        {
            Cookie c = new Cookie("q1", "v1");
            c.setPath("/");
            c.setSecure(false);
            c.setHttpOnly(true);

            return new TestData(c, "q1=v1; Path=/; HttpOnly;");
        }
        if (7 == repetition)
        {
            Cookie c = new Cookie("q1", "v1");
            c.setPath("/");
            c.setSecure(false);
            c.setHttpOnly(true);
            c.setMaxAge(60);

            return new TestData(c, "q1=v1; Path=/; HttpOnly; Max-Age=60;");
        }

        throw getRepeatException(repetition);
    }

    @RepeatedTest(7)
    public void addingValidCookie_cookieStringSetOnResponse(RepetitionInfo repetitionInfo)
    {
        // Arrange
        TestData td = provider(repetitionInfo.getCurrentRepetition());

        HttpServletResponse httpServletResponseMock = MockCreator.getHttpServletResponseMock();

        // Act
        serviceFactory.getSpringCookieService().set(httpServletResponseMock, td.testedCookie);

        // Assert
        verify(httpServletResponseMock).setHeader("Set-Cookie", td.expectedCookieString);
    }
}
