package com.kbalazsworks.stackjudge.integration.fake_builders;


import com.kbalazsworks.stackjudge.domain.entities.Company;

import java.time.LocalDateTime;
import java.util.List;

public class CompanyFakeBuilder
{
    private Long          id            = 164985367L;
    private String        name          = "a company";
    private short         companySizeId = 2;
    private short         itSizeId      = 3;
    private String        logoPath      = "folder/file.jpg";
    private LocalDateTime createdAt     = LocalDateTime.of(2020, 1, 2, 3, 4, 5);
    private Long          createdBy     = 444L;

    public List<Company> buildAsList()
    {
        return List.of(build());
    }

    public Company build()
    {
        return new Company(
            id,
            name,
            companySizeId,
            itSizeId,
            logoPath,
            createdAt,
            createdBy
        );
    }

    public CompanyFakeBuilder setId(Long id)
    {
        this.id = id;

        return this;
    }

    public CompanyFakeBuilder setName(String name)
    {
        this.name = name;

        return this;
    }

    public CompanyFakeBuilder setCompanySizeId(short companySizeId)
    {
        this.companySizeId = companySizeId;

        return this;
    }

    public CompanyFakeBuilder setItSizeId(short itSizeId)
    {
        this.itSizeId = itSizeId;

        return this;
    }

    public CompanyFakeBuilder setLogoPath(String logoPath)
    {
        this.logoPath = logoPath;

        return this;
    }

    public CompanyFakeBuilder setCreatedAt(LocalDateTime createdAt)
    {
        this.createdAt = createdAt;

        return this;
    }

    public CompanyFakeBuilder setCreatedBy(Long createdBy)
    {
        this.createdBy = createdBy;

        return this;
    }
}
