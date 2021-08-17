package com.kbalazsworks.stackjudge.api.services;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FacebookService
{
    // @todo: hello redis
    public static List<String> stateStore = new ArrayList<>();

    private final ApplicationProperties applicationProperties;
    private final SecureRandomService   secureRandomService;

    // @todo: test
    public String registrationAndLogin()
    {
        String currentStateId = "secret_" + secureRandomService.getUrlEncoded(32);
        FacebookService.stateStore.add(currentStateId);

        OAuth20Service service = new ServiceBuilder(applicationProperties.getFacebookClientId())
            .apiSecret(applicationProperties.getFacebookClientSecret())
            .callback(applicationProperties.getFacebookCallbackUrl())
            .build(FacebookLatestApiService.instance());

        return service.createAuthorizationUrlBuilder()
            .state(currentStateId)
            .build();
    }
}
