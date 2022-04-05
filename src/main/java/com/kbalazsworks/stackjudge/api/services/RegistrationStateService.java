package com.kbalazsworks.stackjudge.api.services;

import com.kbalazsworks.stackjudge.api.entities.RegistrationSecret;
import com.kbalazsworks.stackjudge.api.repositories.RegistrationSecretRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Log4j2
public class RegistrationStateService
{
    private final RegistrationSecretRepository              registrationSecretRepository;
    private final RedisTemplate<String, RegistrationSecret> redisTemplate;

    public void add(String state, int timeoutHours)
    {
        log.error("State created (md5): {}", DigestUtils.md5DigestAsHex(state.getBytes()));
        registrationSecretRepository.save(new RegistrationSecret(state, state));
        redisTemplate.expire(state, timeoutHours, TimeUnit.HOURS);
    }

    public boolean exists(String state)
    {
        return registrationSecretRepository.existsById(state);
    }

    public void delete(String state)
    {
        log.error("State deleted (md5): {}", DigestUtils.md5DigestAsHex(state.getBytes()));
        registrationSecretRepository.deleteById(state);
    }
}
