package com.kbalazsworks.stackjudge.api.value_objects;

import com.kbalazsworks.stackjudge.domain.entities.Notification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

@AllArgsConstructor
@Getter
// @todo: convert to record, but JOOQ-JSONP has same problem with that
public class NotificationResponse
{
    @JsonProperty List<Notification> notifications;
    @JsonProperty boolean            hasNew;
}
