package com.kbalazsworks.stackjudge.domain.notification_module.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.codehaus.jackson.annotate.JsonProperty;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
// @todo: convert to record, but JOOQ-JSONP has same problem with that
public class RawNotification
{
    @JsonProperty
    private final Long          id;
    @JsonProperty
    private final String        idsUserId;
    @JsonProperty
    private final short         type;
    @JsonProperty
    private final String        data;
    @JsonProperty
    private final LocalDateTime createdAt;
    @JsonProperty
    private final LocalDateTime viewedAt;
}
