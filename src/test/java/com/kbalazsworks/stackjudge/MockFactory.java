package com.kbalazsworks.stackjudge;

import com.kbalazsworks.stackjudge.domain.common_module.factories.DateFactory;
import com.kbalazsworks.stackjudge.domain.common_module.factories.LocalDateTimeFactory;
import com.kbalazsworks.stackjudge.fake_builders.UserFakeBuilder;
import com.kbalazsworks.stackjudge.state.entities.State;
import com.kbalazsworks.stackjudge.state.services.StateService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Component
@RequiredArgsConstructor
public class MockFactory
{
    private final ServiceFactory serviceFactory;

    public static final LocalDateTime testLocalDateTimeMock1 = LocalDateTime.of(2011, 1, 2, 3, 4, 5);
    public static final LocalDateTime testLocalDateTimeMock2 = LocalDateTime.of(2021, 6, 7, 8, 8, 9);

    public static final State TEST_STATE = new State(testLocalDateTimeMock1, new UserFakeBuilder().build());

    public static StateService getTestStateMock()
    {
        StateService mock = mock(StateService.class);
        when(mock.getState()).thenReturn(new State(TEST_STATE.now(), TEST_STATE.currentUser()));

        return mock;
    }

    /***
     * {
     *   "alg": "HS512"
     * }
     * {
     *   "sub": "105001,Db test user name,http://facebook.com/profile.jpg",
     *   "iss": "dev.stackjudge.com",
     *   "iat": 1609582953,
     *   "exp": 1610187753
     * }
     * HMACSHA512(
     *   base64UrlEncode(header) + "." +
     *   base64UrlEncode(payload),
     *   your-256-bit-secret
     * )
     * created by ApplicationPropertiesMocker.getDefaultMock() && UserFakeBuilder
     */
    public static final String JWT_FOR_USER_FAKE_BUILDER
        = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMDUwMDEsRGIgdGVzdCB1c2VyIG5hbWUsaHR0cDovL2ZhY2Vib29rLmNvbS9wcm9maWxlLmpwZyIsImlzcyI6ImRldi5zdGFja2p1ZGdlLmNvbSIsImlhdCI6MTYwOTU4Mjk1MywiZXhwIjoxNjEwMTg3NzUzfQ.tv4Tx1iuFHkHhruVqWj6FH_6OlOXJMHlDe4wURuJRLz2_READaQQll_W6Sp8uuWIO58LdX3uO1ClcZuKz8mFQg";

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

        return new LocalDateTimeFactory(dateFactory);
    }

    public static void SessionService_getStateMock(StateService stateService)
    {
        when(stateService.getState()).thenReturn(TEST_STATE);
    }
}
