package com.kbalazsworks.stackjudge.domain.entities;

import java.time.LocalDateTime;

public record Group(
    Long id,
    Long parentId,
    long companyId,
    long addressId,
    String name,
    short typeId,
    short membersOnGroupId,
    LocalDateTime createdAt,
    Long createdBy
)
{
}
