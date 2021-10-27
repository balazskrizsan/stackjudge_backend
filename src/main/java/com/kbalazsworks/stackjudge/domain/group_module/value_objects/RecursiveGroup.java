package com.kbalazsworks.stackjudge.domain.group_module.value_objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RecursiveGroup(
    @JsonProperty Long id,
    @JsonProperty String name,
    @JsonProperty short typeId,
    @JsonProperty Long companyId,
    @JsonProperty Long addressId,
    @JsonProperty Long parentId,
    @JsonProperty int depth,
    @JsonProperty String path
)
{
}
