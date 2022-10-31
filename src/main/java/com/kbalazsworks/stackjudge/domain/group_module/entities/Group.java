package com.kbalazsworks.stackjudge.domain.group_module.entities;

import java.time.LocalDateTime;

public record Group(
    Long id,
    Long parentId,
    long companyId,
    Long addressId,
    String name,
    short typeId,
    short membersOnGroupId,
    LocalDateTime createdAt,
    String createdBy
)
{
}
