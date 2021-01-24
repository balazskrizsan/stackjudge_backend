package com.kbalazsworks.stackjudge.fake_builders;

import com.kbalazsworks.stackjudge.domain.entities.Group;

import java.time.LocalDateTime;

public class GroupFakeBuilder
{
    public static final Long defaultId1 = 101001L;

    private long          id               = defaultId1;
    private long          companyId        = 100001L;
    private Long          parentId         = null;
    private short         typeId           = 1;
    private String        name             = "name of group";
    private short         membersOnGroupId = 3;
    private LocalDateTime createdAt        = LocalDateTime.of(2020, 1, 2, 3, 4, 5);
    private Long          createdBy        = 64897L;

    public Group build()
    {
        return new Group(id, companyId, parentId, typeId, name, membersOnGroupId, createdAt, createdBy);
    }

    public GroupFakeBuilder setId(long id)
    {
        this.id = id;

        return this;
    }

    public GroupFakeBuilder setCompanyId(long companyId)
    {
        this.companyId = companyId;

        return this;
    }

    public GroupFakeBuilder setParentId(Long parentId)
    {
        this.parentId = parentId;

        return this;
    }

    public GroupFakeBuilder setTypeId(short typeId)
    {
        this.typeId = typeId;

        return this;
    }

    public GroupFakeBuilder setName(String name)
    {
        this.name = name;

        return this;
    }

    public GroupFakeBuilder setMembersOnGroupId(short membersOnGroupId)
    {
        this.membersOnGroupId = membersOnGroupId;

        return this;
    }

    public GroupFakeBuilder setCreatedAt(LocalDateTime createdAt)
    {
        this.createdAt = createdAt;

        return this;
    }

    public GroupFakeBuilder setCreatedBy(Long createdBy)
    {
        this.createdBy = createdBy;

        return this;
    }
}
