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
    public static final long companyId         = CompanyFakeBuilder.defaultId1;
    public static final int  stackCount        = 222;
    public static final int  teamsCount        = 333;
    public static final int  reviewCount       = 444;
    public static final int  technologiesCount = 555;

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
