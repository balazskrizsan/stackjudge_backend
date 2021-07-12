package com.kbalazsworks.stackjudge.domain.services;

import com.kbalazsworks.stackjudge.domain.entities.ProtectedReviewLog;
import com.kbalazsworks.stackjudge.domain.repositories.ProtectedReviewLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProtectedReviewLogService
{
    private final ProtectedReviewLogRepository protectedReviewLogRepository;

    // @todo: test
    public void create(ProtectedReviewLog protectedReviewLog)
    {
        protectedReviewLogRepository.create(protectedReviewLog);
    }
}
