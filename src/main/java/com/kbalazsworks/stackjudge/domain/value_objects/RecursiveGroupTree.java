package com.kbalazsworks.stackjudge.domain.value_objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record RecursiveGroupTree(
    @JsonProperty RecursiveGroup recursiveGroup,
    @JsonProperty List<RecursiveGroupTree> children
)
{
}
