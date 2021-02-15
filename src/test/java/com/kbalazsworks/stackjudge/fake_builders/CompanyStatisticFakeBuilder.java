package com.kbalazsworks.stackjudge.fake_builders;

import com.kbalazsworks.stackjudge.domain.value_objects.CompanyStatistic;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(fluent = true)
@Getter
@Setter
public class CompanyStatisticFakeBuilder
{
    private long companyId         = CompanyFakeBuilder.defaultId1;
    private int  stackCount        = 222;
    private int  teamsCount        = 333;
    private int  reviewCount       = 444;
    private int  technologiesCount = 555;

    public List<CompanyStatistic> buildAsList()
    {
        return List.of(build());
    }

    public CompanyStatistic build()
    {
        return new CompanyStatistic(
            companyId,
            stackCount,
            teamsCount,
            reviewCount,
            technologiesCount
        );
    }
}
