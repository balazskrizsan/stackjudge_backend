package com.kbalazsworks.stackjudge.api.services;

import com.kbalazsworks.stackjudge.api.entities.RegistrationSecret;
import com.kbalazsworks.stackjudge.api.repositories.RegistrationSecretRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RegistrationStateService
{
    private final RegistrationSecretRepository              registrationSecretRepository;
    private final RedisTemplate<String, RegistrationSecret> redisTemplate;

    // @todo: test
    public void add(String state)
    {
        registrationSecretRepository.save(new RegistrationSecret(state, state));
        redisTemplate.expire(state, 24, TimeUnit.HOURS);
    }

    // @todo2: test
    public boolean exists(String state)
    {
        return registrationSecretRepository.existsById(state);
    }

    // @todo2: test
    public void delete(String state)
    {
        registrationSecretRepository.deleteById(state);
    }
}
