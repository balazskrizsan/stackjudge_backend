package com.kbalazsworks.stackjudge.unit.domain.services.search_my_notifications_service;

import com.kbalazsworks.stackjudge.AbstractTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.domain.entities.ITypedNotification;
import com.kbalazsworks.stackjudge.domain.entities.notification.DataProtectedReview;
import com.kbalazsworks.stackjudge.fake_builders.DataProtectedReviewFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.TypedNotificationFakeBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GetUserIdsFromDataProtectedReviewTypeTest extends AbstractTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    @Test
    public void getUserIdsOnlyFromDataProtectedReviewType_returnsOnlyThisTypeIds()
    {
        // Arrange
        List<ITypedNotification> testedNotifications = List.of(
            new TypedNotificationFakeBuilder<DataProtectedReview>()
                .data(new DataProtectedReviewFakeBuilder().viewerUserId(1L).build())
                .build(),
            new TypedNotificationFakeBuilder<DataProtectedReview>()
                .data(new DataProtectedReviewFakeBuilder().viewerUserId(2L).build())
                .build(),
            new TypedNotificationFakeBuilder<>().type((short) 3).data(new Object()).build()
        );

        List<Long> expectedIds = List.of(1L, 2L);

        // Act
        List<Long> actual = serviceFactory
            .getSearchMyNotificationsService()
            .getUserIdsFromDataProtectedReviewType(testedNotifications);

        // Assert
        assertThat(actual).isEqualTo(expectedIds);
    }
}
