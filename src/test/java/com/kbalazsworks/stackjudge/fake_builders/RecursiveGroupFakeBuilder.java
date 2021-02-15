package com.kbalazsworks.stackjudge.fake_builders;

import com.kbalazsworks.stackjudge.domain.value_objects.RecursiveGroup;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
@Setter
public class RecursiveGroupFakeBuilder
{
    private Long   id        = 101001L;
    private String name      = "group name";
    private Long   companyId = 100001L;
    private Long   parentId  = null;
    private int    depth     = 1;
    private String path      = "101001";
    
    public RecursiveGroup build()
    {
        return new RecursiveGroup(id, name, companyId, parentId, depth, path);
    }
}
