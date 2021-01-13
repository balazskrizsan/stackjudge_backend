package com.kbalazsworks.stackjudge.integration.fake_builders;

import com.kbalazsworks.stackjudge.domain.value_objects.RecursiveGroup;

public class RecursiveGroupFakeBuilder
{
    private Long   id        = 16521654L;
    private String name      = "group name";
    private Long   companyId = 164985367L;
    private Long   parentId  = null;
    private int    depth     = 1;
    private String path      = "16521654";
    
    public RecursiveGroup build()
    {
        return new RecursiveGroup(id, name, companyId, parentId, depth, path);
    }

    public RecursiveGroupFakeBuilder setId(Long id)
    {
        this.id = id;

        return this;
    }

    public RecursiveGroupFakeBuilder setName(String name)
    {
        this.name = name;

        return this;
    }

    public RecursiveGroupFakeBuilder setCompanyId(Long companyId)
    {
        this.companyId = companyId;

        return this;
    }

    public RecursiveGroupFakeBuilder setParentId(Long parentId)
    {
        this.parentId = parentId;

        return this;
    }

    public RecursiveGroupFakeBuilder setDepth(int depth)
    {
        this.depth = depth;

        return this;
    }

    public RecursiveGroupFakeBuilder setPath(String path)
    {
        this.path = path;

        return this;
    }
}
