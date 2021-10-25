package com.kbalazsworks.stackjudge.domain.persistance_log_module.services;

import com.kbalazsworks.stackjudge.domain.entities.TypedPersistenceLog;
import com.kbalazsworks.stackjudge.domain.persistance_log_module.repositories.PersistenceLogRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class PersistenceLogService
{
    private final PersistenceLogRepository persistenceLogRepository;

    public <T> void create(@NonNull TypedPersistenceLog<T> typedPersistenceLog)
    {
        try
        {
            persistenceLogRepository.create(typedPersistenceLog);
        }
        catch (Exception e)
        {
            log.error("PersistenceLogService error: " + e.getMessage());
        }
    }
}
