package com.kbalazsworks.stackjudge.fake_builders;


import com.kbalazsworks.stackjudge.domain.entities.Company;

import java.time.LocalDateTime;
import java.util.List;

public class CompanyFakeBuilder
{
    public static final Long defaultId1  = 100001L;
    public static final Long defaultId2  = 100002L;
    public static final Long defaultId3  = 100003L;
    public static final Long defaultId4  = 100004L;
    public static final Long defaultId5  = 100005L;
    public static final Long defaultId6  = 100006L;
    public static final Long defaultId7  = 100007L;
    public static final Long defaultId8  = 100008L;
    public static final Long defaultId9  = 100009L;
    public static final Long defaultId10 = 100010L;

    private Long          id            = defaultId1;
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
