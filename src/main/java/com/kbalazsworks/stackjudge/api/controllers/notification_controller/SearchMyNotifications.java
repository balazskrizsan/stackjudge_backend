package com.kbalazsworks.stackjudge.api.controllers.notification_controller;

import com.kbalazsworks.stackjudge.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.stackjudge.api.exceptions.ApiException;
import com.kbalazsworks.stackjudge.api.requests.notification_requests.SearchMyNotificationsRequest;
import com.kbalazsworks.stackjudge.domain.value_objects.NotificationResponse;
import com.kbalazsworks.stackjudge.api.value_objects.ResponseData;
import com.kbalazsworks.stackjudge.domain.services.NotificationService;
import com.kbalazsworks.stackjudge.state.services.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;

@RestController("NotificationSearchMyNotificationsAction")
@RequestMapping(NotificationConfig.CONTROLLER_URI)
@RequiredArgsConstructor
public class SearchMyNotifications
{
    private final NotificationService notificationService;
    private final StateService        stateService;

    @GetMapping(NotificationConfig.SEARCH_MY_NOTIFICATIONS_ACTION)
    public ResponseEntity<ResponseData<NotificationResponse>> action(
        @RequestPayload SearchMyNotificationsRequest request
    ) throws ApiException
    {
        return new ResponseEntityBuilder<NotificationResponse>()
            .data(notificationService.searchMyNotifications(request.limit(), stateService.getState()))
            .build();
    }
}
