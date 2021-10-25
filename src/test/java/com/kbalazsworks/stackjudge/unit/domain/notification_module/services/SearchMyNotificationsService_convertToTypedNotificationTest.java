package com.kbalazsworks.stackjudge.unit.domain.notification_module.services;

import com.kbalazsworks.stackjudge.AbstractTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.domain.entities.ITypedNotification;
import com.kbalazsworks.stackjudge.domain.entities.RawNotification;
import com.kbalazsworks.stackjudge.fake_builders.DataProtectedReviewFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.RawNotificationFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.TypedNotificationFakeBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchMyNotificationsService_convertToTypedNotificationTest extends AbstractTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    @Test
    public void checkAllTypeOfNotification_perfect()
    {
        // Arrange
        List<RawNotification> testedNotifications = List.of(new RawNotificationFakeBuilder().build());
        List<ITypedNotification> expectedNotifications = List.of(
            new TypedNotificationFakeBuilder<>()
                .data(new DataProtectedReviewFakeBuilder().build())
                .build()
        );

        // Act
        List<ITypedNotification> actualList = serviceFactory.getSearchMyNotificationsService()
            .convertToTypedNotification(testedNotifications);

        // Assert
        assertThat(actualList).usingRecursiveComparison().isEqualTo(expectedNotifications);
    }
}
