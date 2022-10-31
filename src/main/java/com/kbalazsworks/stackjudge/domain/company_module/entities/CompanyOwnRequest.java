package com.kbalazsworks.stackjudge.domain.company_module.entities;

import java.time.LocalDateTime;

public record CompanyOwnRequest(
    String requesterIdsUserId,
    Long requestedCompanyId,
    String secret,
    LocalDateTime createdAt
)
{
}