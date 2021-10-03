package com.kbalazsworks.stackjudge.domain.services;

import com.kbalazsworks.stackjudge.domain.entities.RegistrationSecret;
import com.kbalazsworks.stackjudge.domain.repositories.RegistrationSecretRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationStateService
{
    private final RegistrationSecretRepository registrationSecretRepository;

    // @todo2: test
    public void add(String state)
    {
        registrationSecretRepository.save(new RegistrationSecret(state, state));
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
