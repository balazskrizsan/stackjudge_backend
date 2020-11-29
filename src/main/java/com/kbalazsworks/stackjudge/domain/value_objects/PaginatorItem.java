package com.kbalazsworks.stackjudge.domain.value_objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbalazsworks.stackjudge.domain.enums.paginator.NavigationEnum;
import com.kbalazsworks.stackjudge.domain.enums.paginator.ItemTypeEnum;

public record PaginatorItem(
    @JsonProperty ItemTypeEnum typeId,
    @JsonProperty String pageNumber,
    @JsonProperty NavigationEnum navigation,
    @JsonProperty boolean isActive
)
{
}
