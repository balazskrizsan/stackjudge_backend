package com.kbalazsworks.stackjudge.api.value_objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FacebookUser(
        @JsonProperty("id") Long id,
        @JsonProperty("name") String name
) {
}
