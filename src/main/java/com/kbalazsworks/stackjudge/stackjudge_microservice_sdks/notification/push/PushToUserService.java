package com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.notification.push;

import com.kbalazsworks.simple_oidc.exceptions.OidcApiException;
import com.kbalazsworks.simple_oidc.services.ICommunicationService;
import com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.open_sdk_module.services.NotificationOpenSdkService;
import com.kbalazsworks.stackjudge_notification_sdk.common.exceptions.ResponseException;
import com.kbalazsworks.stackjudge_notification_sdk.common.interfaces.IOpenSdkPostable;
import com.kbalazsworks.stackjudge_notification_sdk.schema_interfaces.IPushTouser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import static com.kbalazsworks.stackjudge.common.enums.OidcGrantNamesEnum.NOTIFICATION__SEND_PUSH;

@Service
@RequiredArgsConstructor
@Log4j2
public class PushToUserService implements IPushTouser
{
    private final NotificationOpenSdkService notificationOpenSdkService;
    private final ICommunicationService      communicationService;

    @Override
    public void execute(IOpenSdkPostable pushToUserRequest) throws ResponseException
    {
        try
        {
            String accessToken = communicationService
                .callTokenEndpoint(NOTIFICATION__SEND_PUSH.getValue())
                .getAccessToken();

            HttpHeaders headers = new HttpHeaders()
            {{
                setBearerAuth(accessToken);
            }};

            notificationOpenSdkService.post(pushToUserRequest, getApiUri(), headers);
        }
        catch (OidcApiException e)
        {
            log.error("OIDC error: {}", e.getMessage(), e);

            throw new ResponseException("Invalid service response");
        }
    }
}
