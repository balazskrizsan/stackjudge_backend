package com.kbalazsworks.stackjudge.unit.domain.notification_module.services;

import com.kbalazsworks.stackjudge.AbstractTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.domain.entities.ITypedNotification;
import com.kbalazsworks.stackjudge.fake_builders.TypedNotificationFakeBuilder;
import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchMyNotificationsService_hasNewTest extends AbstractTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    @Test
    public void VintageHack()
    {
        assertThat(true).isTrue();
    }

    private record TestData(List<ITypedNotification> testedNotifications, boolean expectedResult)
    {
    }

    private TestData provider(int repetition)
    {
        if (1 == repetition)
        {
            return new TestData(
                new ArrayList<>()
                {{
                    add(new TypedNotificationFakeBuilder<>().build());
                    add(new TypedNotificationFakeBuilder<>().viewedAt(null).build());
                }},
                true
            );
        }

        if (2 == repetition)
        {
            return new TestData(
                new ArrayList<>()
                {{
                    add(new TypedNotificationFakeBuilder<>().build());
                    add(new TypedNotificationFakeBuilder<>().build());
                }},
                false
            );
        }

        throw getRepeatException(repetition);
    }

    @RepeatedTest(2)
    public void listHasNewItem_returnsTrue(RepetitionInfo repetitionInfo)
    {
        // Arrange
        TestData testData = provider(repetitionInfo.getCurrentRepetition());

        // Act
        boolean actual = serviceFactory.getSearchMyNotificationsService().hasNew(testData.testedNotifications());

        // Assert
        assertThat(actual).isEqualTo(testData.expectedResult());
    }
}
