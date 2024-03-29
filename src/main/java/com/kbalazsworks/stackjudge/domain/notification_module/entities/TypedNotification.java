package com.kbalazsworks.stackjudge.domain.notification_module.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.codehaus.jackson.annotate.JsonProperty;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
// @todo: convert to record, but JOOQ-JSONP has same problem with that
public class TypedNotification<T> implements ITypedNotification
{
    @JsonProperty
    private final Long          id;
    @JsonProperty
    private final String        idsUserId;
    @JsonProperty
    private final short         type;
    @JsonProperty
    private final T             data;
    @JsonProperty
    private final LocalDateTime createdAt;
    @JsonProperty
    private final LocalDateTime viewedAt;
}
