package com.kbalazsworks.stackjudge.api.services;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.AuthorizationUrlBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import static java.util.concurrent.TimeUnit.MINUTES;

@Service
@RequiredArgsConstructor
public class FacebookService
{
    private final SecureRandom stateGenerator = new SecureRandom();
    private final SecureRandomService   secureRandomService;

    // @todo: hello redis
    public static List<String> stateStore = new ArrayList<>();

    private final ApplicationProperties applicationProperties;
    private final RedisTemplate<String, String> redisTemplate;

    // @todo: test
    public String registrationAndLogin() {
        String currentStateId = "secret_" + secureRandomService.get(32);
        FacebookService.stateStore.add(currentStateId);

        OAuth20Service service = oAuthService();

        AuthorizationUrlBuilder authorizationUrlBuilder = service.createAuthorizationUrlBuilder()
                .state(currentStateId)
                .initPKCE();

        cacheCodeVerifier(authorizationUrlBuilder.getPkce().getCodeVerifier());

        return authorizationUrlBuilder.build();
    }

    private OAuth20Service oAuthService() {
        return new ServiceBuilder(applicationProperties.getFacebookClientId())
                .apiSecret(applicationProperties.getFacebookClientSecret())
                .callback(applicationProperties.getFacebookCallbackUrl())
                .build(FacebookLatestApiService.instance());
    }

    private void cacheCodeVerifier(String codeVerifier) {
        redisTemplate.opsForValue().set(
                applicationProperties.getFacebookClientId(),
                codeVerifier,
                5,
                MINUTES
        );
    }
}
