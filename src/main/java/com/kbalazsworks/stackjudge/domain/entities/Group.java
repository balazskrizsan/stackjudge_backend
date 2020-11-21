package com.kbalazsworks.stackjudge.domain.entities;

import java.time.LocalDateTime;

public record Group(
    Long id,
    long companyId,
    Long parentId,
    short typeId,
    String name,
    short membersOnGroupId,
    LocalDateTime createdAt,
    Long createdBy
)
{
}
