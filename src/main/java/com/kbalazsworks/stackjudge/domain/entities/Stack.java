package com.kbalazsworks.stackjudge.domain.entities;

import java.time.LocalDateTime;

public record Stack(
    Long id,
    long companyId,
    Long parentId,
    short typeId,
    String name,
    short membersOnStackId,
    LocalDateTime createdAt,
    Long createdBy
)
{
}
