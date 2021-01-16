package com.kbalazsworks.stackjudge.fake_builders;

import com.kbalazsworks.stackjudge.domain.value_objects.RecursiveGroup;
import com.kbalazsworks.stackjudge.domain.value_objects.RecursiveGroupTree;

import java.util.List;

public class RecursiveGroupTreeFakeBuilder
{
    private RecursiveGroup           recursiveGroup;
    private List<RecursiveGroupTree> children;

    public List<RecursiveGroupTree> buildAsList()
    {
        return List.of(build());
    }

    public RecursiveGroupTree build()
    {
        return new RecursiveGroupTree(new RecursiveGroupFakeBuilder().build(), null);
    }

    public RecursiveGroupTreeFakeBuilder setRecursiveGroup(RecursiveGroup recursiveGroup)
    {
        this.recursiveGroup = recursiveGroup;

        return this;
    }

    public RecursiveGroupTreeFakeBuilder setChildren(List<RecursiveGroupTree> children)
    {
        this.children = children;

        return this;
    }
}
