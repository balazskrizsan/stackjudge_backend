package com.kbalazsworks.stackjudge.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record Review(
    @JsonProperty Long id,
    @JsonProperty long groupId,
    @JsonProperty short visibility,
    @JsonProperty short rate,
    @JsonProperty String review,
    @JsonProperty LocalDateTime createdAt,
    @JsonProperty Long createdBy
)
{
}
