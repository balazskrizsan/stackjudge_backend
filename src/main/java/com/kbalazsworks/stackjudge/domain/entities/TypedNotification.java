package com.kbalazsworks.stackjudge.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.codehaus.jackson.annotate.JsonProperty;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
// @todo: convert to record, but JOOQ-JSONP has same problem with that
public class TypedNotification<T> implements ITypedNotification
{
    @JsonProperty private final Long          id;
    @JsonProperty private final long          userId;
    @JsonProperty private final short         type;
    @JsonProperty private final T             data;
    @JsonProperty private final LocalDateTime createdAt;
    @JsonProperty private final LocalDateTime viewedAt;
}
