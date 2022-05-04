package com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.notification.push;

import com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.open_sdk_module.services.NotificationOpenSdkService;
import com.kbalazsworks.stackjudge_notification_sdk.common.exceptions.ResponseException;
import com.kbalazsworks.stackjudge_notification_sdk.common.interfaces.IOpenSdkPostable;
import com.kbalazsworks.stackjudge_notification_sdk.schema_interfaces.IPushTouser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PushToUserService implements IPushTouser
{
    private final NotificationOpenSdkService notificationOpenSdkService;

    @Override
    public void execute(IOpenSdkPostable pushToUserRequest) throws ResponseException
    {
        notificationOpenSdkService.post(pushToUserRequest, getApiUri());
    }
}
