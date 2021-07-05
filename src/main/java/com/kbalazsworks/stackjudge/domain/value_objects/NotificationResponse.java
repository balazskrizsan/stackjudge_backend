package com.kbalazsworks.stackjudge.domain.value_objects;

import com.kbalazsworks.stackjudge.domain.entities.ITypedNotification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

@AllArgsConstructor
@Getter
// @todo: convert to record, but JOOQ-JSONP has same problem with that
public class NotificationResponse
{
    @JsonProperty List<ITypedNotification> rawNotifications;
    @JsonProperty boolean                  hasNew;
    @JsonProperty long                     newCount;
}
