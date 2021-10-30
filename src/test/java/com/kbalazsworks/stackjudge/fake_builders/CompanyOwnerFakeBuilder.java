package com.kbalazsworks.stackjudge.fake_builders;

import com.kbalazsworks.stackjudge.domain.company_module.entities.CompanyOwner;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Accessors(fluent = true)
@Getter
@Setter
public class CompanyOwnerFakeBuilder
{
    public static final long defaultId = 100001;

    private long          companyId = defaultId;
    private long          userId    = 105001;
    private LocalDateTime createdAt = LocalDateTime.of(2021, 1, 17, 2, 30, 0);

    public CompanyOwner build()
    {
        return new CompanyOwner(companyId, userId, createdAt);
    }
}
