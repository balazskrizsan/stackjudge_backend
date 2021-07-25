package com.kbalazsworks.stackjudge.api.controllers.notification_controller;

import com.kbalazsworks.stackjudge.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.stackjudge.api.exceptions.ApiException;
import com.kbalazsworks.stackjudge.api.requests.notification_requests.DeleteRequest;
import com.kbalazsworks.stackjudge.api.value_objects.ResponseData;
import com.kbalazsworks.stackjudge.domain.services.CrudNotificationService;
import com.kbalazsworks.stackjudge.state.services.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;

@RestController("NotificationDeleteAction")
@RequestMapping(NotificationConfig.CONTROLLER_URI)
@RequiredArgsConstructor
public class DeleteAction
{
    private final CrudNotificationService crudNotificationService;
    private final StateService            stateService;

    @DeleteMapping("{notificationId}")
    public ResponseEntity<ResponseData<String>> action(@RequestPayload DeleteRequest request)
        throws ApiException
    {
        crudNotificationService.delete(request.getNotificationId(), stateService.getState());

        return new ResponseEntityBuilder<String>().data(null).build();
    }
}
