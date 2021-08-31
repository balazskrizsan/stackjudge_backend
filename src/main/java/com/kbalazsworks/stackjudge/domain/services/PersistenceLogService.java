package com.kbalazsworks.stackjudge.domain.services;

import com.kbalazsworks.stackjudge.domain.entities.TypedPersistenceLog;
import com.kbalazsworks.stackjudge.domain.repositories.PersistenceLogRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersistenceLogService
{
    private final PersistenceLogRepository persistenceLogRepository;

    public <T> void create(@NonNull TypedPersistenceLog<T> typedPersistenceLog)
    {
        persistenceLogRepository.create(typedPersistenceLog);
    }
}
