package com.kbalazsworks.stackjudge.fake_builders;

import com.kbalazsworks.stackjudge.domain.value_objects.CompanyStatistic;

import java.util.List;

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

    public CompanyStatisticFakeBuilder setCompanyId(long companyId)
    {
        this.companyId = companyId;

        return this;
    }

    public CompanyStatisticFakeBuilder setStackCount(int stackCount)
    {
        this.stackCount = stackCount;

        return this;
    }

    public CompanyStatisticFakeBuilder setTeamsCount(int teamsCount)
    {
        this.teamsCount = teamsCount;

        return this;
    }

    public CompanyStatisticFakeBuilder setReviewCount(int reviewCount)
    {
        this.reviewCount = reviewCount;

        return this;
    }

    public CompanyStatisticFakeBuilder setTechnologiesCount(int technologiesCount)
    {
        this.technologiesCount = technologiesCount;

        return this;
    }
}
