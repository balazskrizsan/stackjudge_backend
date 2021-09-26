package com.kbalazsworks.stackjudge.fake_builders;

import com.kbalazsworks.stackjudge.domain.entities.CompanyOwner;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Accessors(fluent = true)
@Getter
@Setter
public class CompanyOwnerFakeBuilder
{

    private final long          companyId = 100001;
    private final long          userId    = 105001;
    private final LocalDateTime createdAt = LocalDateTime.of(2021, 1, 17, 2, 30, 0);

    public CompanyOwner build()
    {
        return new CompanyOwner(companyId, userId, createdAt);
    }
}
