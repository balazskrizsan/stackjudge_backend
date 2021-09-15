package com.kbalazsworks.stackjudge.domain.entities;

import lombok.NonNull;

import java.time.LocalDateTime;

public record CompanyOwner(
    long companyId,
    long userId,
    @NonNull LocalDateTime createdAt
)
{
}
