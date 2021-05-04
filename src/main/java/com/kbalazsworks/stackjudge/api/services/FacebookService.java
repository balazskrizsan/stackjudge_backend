package com.kbalazsworks.stackjudge.api.services;

import com.github.scribejava.apis.FacebookApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class FacebookService
{
    private ApplicationProperties applicationProperties;

    @Autowired
    public void setApplicationProperties(ApplicationProperties applicationProperties)
    {
        this.applicationProperties = applicationProperties;
    }

    public String registrationAndLogin()
    {
        OAuth20Service service = new ServiceBuilder(applicationProperties.getFacebookClientId())
            .apiSecret(applicationProperties.getFacebookClientSecret())
            .callback(applicationProperties.getFacebookCallbackUrl())
            .build(FacebookApi.instance());

        return service.getAuthorizationUrl("secret" + new Random().nextInt(999_999));
    }
}
