package com.kbalazsworks.stackjudge.domain.entities;

import java.time.LocalDateTime;

public record CompanyOwnRequest(
    Long requesterUserId,
    Long requestedCompanyId,
    String secret,
    LocalDateTime createdAt
)
{
}