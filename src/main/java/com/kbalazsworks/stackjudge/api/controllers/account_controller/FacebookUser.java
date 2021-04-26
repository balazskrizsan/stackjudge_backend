package com.kbalazsworks.stackjudge.api.controllers.account_controller;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FacebookUser(
        @JsonProperty("id") Long id,
        @JsonProperty("name") String name
) {
}
