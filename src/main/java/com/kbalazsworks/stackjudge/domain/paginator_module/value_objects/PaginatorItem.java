package com.kbalazsworks.stackjudge.domain.paginator_module.value_objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbalazsworks.stackjudge.domain.review_module.enums.NavigationEnum;
import com.kbalazsworks.stackjudge.domain.review_module.enums.ItemTypeEnum;

public record PaginatorItem(
    @JsonProperty ItemTypeEnum typeId,
    @JsonProperty String pageNumber,
    @JsonProperty NavigationEnum navigation,
    @JsonProperty boolean active
)
{
}
