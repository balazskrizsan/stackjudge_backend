package com.kbalazsworks.stackjudge.domain.group_module.value_objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record RecursiveGroupTree(
    @JsonProperty RecursiveGroup recursiveGroup,
    @JsonProperty List<RecursiveGroupTree> children
)
{
}
