package com.kbalazsworks.stackjudge.fake_builders;

import com.kbalazsworks.stackjudge.domain.value_objects.RecursiveGroup;
import com.kbalazsworks.stackjudge.domain.value_objects.RecursiveGroupTree;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(fluent = true)
@Getter
@Setter
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
}
