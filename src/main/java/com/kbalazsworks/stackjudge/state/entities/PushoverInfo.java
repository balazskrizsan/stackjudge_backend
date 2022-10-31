package com.kbalazsworks.stackjudge.state.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PushoverInfo(
    @JsonProperty String idsUserId,
    @JsonProperty String pushoverUserToken
)
{
}
