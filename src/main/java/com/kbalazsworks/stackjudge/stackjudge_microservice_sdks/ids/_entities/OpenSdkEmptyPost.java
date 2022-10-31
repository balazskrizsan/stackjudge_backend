package com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.ids._entities;

import com.kbalazsworks.stackjudge_notification_sdk.common.interfaces.IOpenSdkPostable;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class OpenSdkEmptyPost implements IOpenSdkPostable
{
    @Override
    public MultiValueMap<String, Object> toOpenSdkPost()
    {
        return new LinkedMultiValueMap<>();
    }
}
