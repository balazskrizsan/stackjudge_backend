package com.kbalazsworks.stackjudge.api.services;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.kbalazsworks.stackjudge.common.services.SecureRandomService;
import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class FacebookService
{
    private final ApplicationProperties    applicationProperties;
    private final SecureRandomService      secureRandomService;
    private final RegistrationStateService registrationStateService;

    // @todo: test
    public String registrationAndLogin()
    {
        log.info("Login started: Facebook");
        String currentState = secureRandomService.getUrlEncoded(32);

        registrationStateService.add(currentState, 24);

        OAuth20Service service = new ServiceBuilder(applicationProperties.getFacebookClientId())
            .apiSecret(applicationProperties.getFacebookClientSecret())
            .callback(applicationProperties.getFacebookCallbackUrl())
            .build(FacebookLatestApiService.instance());

        return service.createAuthorizationUrlBuilder().state(currentState).build();
    }
}
