package com.kbalazsworks.stackjudge.api.builders;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.kbalazsworks.stackjudge.api.services.facebook_services.ScribeJavaFacebookLatestApiService;
import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuthFacebookServiceBuilder
{
    private final ApplicationProperties applicationProperties;

    public OAuth20Service build()
    {
        return new ServiceBuilder(applicationProperties.getFacebookClientId())
            .apiSecret(applicationProperties.getFacebookClientSecret())
            .callback(applicationProperties.getFacebookCallbackUrl())
            .build(ScribeJavaFacebookLatestApiService.instance());
    }
}
