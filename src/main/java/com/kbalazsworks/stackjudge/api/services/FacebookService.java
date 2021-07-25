package com.kbalazsworks.stackjudge.api.services;

import com.github.scribejava.core.oauth.OAuth20Service;
import com.kbalazsworks.stackjudge.api.builders.OAuthFacebookServiceBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
@Slf4j
@RequiredArgsConstructor
public class FacebookService {
    private final JedisPool jedisPool;
    private final OAuthFacebookServiceBuilder oAuthFacebookServiceBuilder;
    private final SecureRandomService secureRandomService;

    // @todo: test
    public String registrationAndLogin() {

        String currentStateId = "secret_" + secureRandomService.get(32);

        OAuth20Service service = oAuthFacebookServiceBuilder.create();

        saveState(currentStateId);

        return service.createAuthorizationUrlBuilder()
                .state(currentStateId)
                .build();

    }

    private void saveState(String state) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set(state, "");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("The state could not be saved!");
        }
    }

}
