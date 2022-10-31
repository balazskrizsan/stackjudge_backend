package com.kbalazsworks.stackjudge.unit.domain.notification_module.services;

import com.kbalazsworks.stackjudge.AbstractTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.domain.notification_module.entities.ITypedNotification;
import com.kbalazsworks.stackjudge.domain.review_module.entities.DataProtectedReview;
import com.kbalazsworks.stackjudge.fake_builders.DataProtectedReviewFakeBuilder;
import com.kbalazsworks.stackjudge.fake_builders.TypedNotificationFakeBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchMyNotificationsService_getIdsUserIdsFromDataProtectedReviewTypeTest extends AbstractTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    @Test
    public void getUserIdsOnlyFromDataProtectedReviewType_returnsOnlyThisTypeIds()
    {
        // Arrange
        List<ITypedNotification> testedNotifications = List.of(
            new TypedNotificationFakeBuilder<DataProtectedReview>()
                .data(new DataProtectedReviewFakeBuilder().viewerUserId("1").build())
                .build(),
            new TypedNotificationFakeBuilder<DataProtectedReview>()
                .data(new DataProtectedReviewFakeBuilder().viewerUserId("2").build())
                .build(),
            new TypedNotificationFakeBuilder<>().type((short) 3).data(new Object()).build()
        );

        List<String> expectedIds = List.of("1", "2");

        // Act
        List<String> actual = serviceFactory
            .getSearchMyNotificationsService()
            .getUserIdsFromDataProtectedReviewType(testedNotifications);

        // Assert
        assertThat(actual).isEqualTo(expectedIds);
    }
}
