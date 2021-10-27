package com.kbalazsworks.stackjudge.domain.company_module.entities;

import java.time.LocalDateTime;

public record CompanyOwnRequest(
    Long requesterUserId,
    Long requestedCompanyId,
    String secret,
    LocalDateTime createdAt
)
{
}