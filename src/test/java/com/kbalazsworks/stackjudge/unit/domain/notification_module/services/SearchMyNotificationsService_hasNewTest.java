package com.kbalazsworks.stackjudge.unit.domain.notification_module.services;

import com.kbalazsworks.stackjudge.AbstractTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.domain.notification_module.entities.ITypedNotification;
import com.kbalazsworks.stackjudge.fake_builders.TypedNotificationFakeBuilder;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Stream;

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

    static class Provider_listHasNewItem_returnsTrue implements ArgumentsProvider
    {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context)
        {
            return Stream.of(
                Arguments.of(
                    List.of(
                        new TypedNotificationFakeBuilder<>().build(),
                        new TypedNotificationFakeBuilder<>().viewedAt(null).build()
                    ),
                    true
                ),
                Arguments.of(
                    List.of(
                        new TypedNotificationFakeBuilder<>().build(),
                        new TypedNotificationFakeBuilder<>().build()
                    ),
                    false
                )
            );
        }
    }

    @ParameterizedTest(name = "{index} => testedNotifications={0}")
    @ArgumentsSource(Provider_listHasNewItem_returnsTrue.class)
    public void listHasNewItem_returnsTrue(List<ITypedNotification> testedNotifications, boolean expectedResult)
    {
        // Arrange
        // Act
        boolean actual = serviceFactory.getSearchMyNotificationsService().hasNew(testedNotifications);

        // Assert
        assertThat(actual).isEqualTo(expectedResult);
    }
}
