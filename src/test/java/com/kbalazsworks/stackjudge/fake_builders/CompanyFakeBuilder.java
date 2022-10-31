package com.kbalazsworks.stackjudge.fake_builders;


import com.kbalazsworks.stackjudge.domain.company_module.entities.Company;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Accessors(fluent = true)
@Getter
@Setter
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
    private String        domain        = "test-company.com";
    private short         companySizeId = 2;
    private short         itSizeId      = 3;
    private String        logoPath      = "folder/file.jpg";
    private LocalDateTime createdAt     = LocalDateTime.of(2020, 1, 2, 3, 4, 5);
    private String          createdBy     = UserFakeBuilder.defaultId1;

    public List<Company> buildAsList()
    {
        return List.of(build());
    }

    public Company build()
    {
        return new Company(
            id,
            name,
            domain,
            companySizeId,
            itSizeId,
            logoPath,
            createdAt,
            createdBy
        );
    }
}
