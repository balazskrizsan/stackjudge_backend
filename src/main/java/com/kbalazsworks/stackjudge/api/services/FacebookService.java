package com.kbalazsworks.stackjudge.api.services;

import com.github.scribejava.apis.FacebookApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.AuthorizationUrlBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

import static java.util.concurrent.TimeUnit.MINUTES;

@Service
@RequiredArgsConstructor
public class FacebookService
{
    private final SecureRandom stateGenerator = new SecureRandom();

    private final ApplicationProperties applicationProperties;
    private final RedisTemplate<String, String> redisTemplate;

    public String registrationAndLogin() {
        OAuth20Service service = oAuthService();

        AuthorizationUrlBuilder authorizationUrlBuilder = service.createAuthorizationUrlBuilder()
                .state("secret" + stateGenerator.nextInt(999_999))
                .initPKCE();

        cacheCodeVerifier(authorizationUrlBuilder.getPkce().getCodeVerifier());

        return authorizationUrlBuilder.build();
    }

    private OAuth20Service oAuthService() {
        return new ServiceBuilder(applicationProperties.getFacebookClientId())
                .apiSecret(applicationProperties.getFacebookClientSecret())
                .callback(applicationProperties.getFacebookCallbackUrl())
                .build(FacebookApi.instance());
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
