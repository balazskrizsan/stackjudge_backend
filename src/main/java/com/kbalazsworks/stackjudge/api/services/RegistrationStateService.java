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

    public void add(String state, int timeoutHours)
    {
        registrationSecretRepository.save(new RegistrationSecret(state, state));
        redisTemplate.expire(state, timeoutHours, TimeUnit.HOURS);
    }

    public boolean exists(String state)
    {
        return registrationSecretRepository.existsById(state);
    }

    public void delete(String state)
    {
        registrationSecretRepository.deleteById(state);
    }
}
