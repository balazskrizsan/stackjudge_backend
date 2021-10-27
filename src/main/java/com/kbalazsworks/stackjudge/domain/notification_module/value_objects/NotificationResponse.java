package com.kbalazsworks.stackjudge.domain.notification_module.value_objects;

import com.kbalazsworks.stackjudge.domain.notification_module.entities.ITypedNotification;
import com.kbalazsworks.stackjudge.state.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
// @todo3: convert to record, but JOOQ-JSONP has same problem with that
public class NotificationResponse
{
    @JsonProperty List<ITypedNotification> notifications;
    @JsonProperty boolean                  hasNew;
    @JsonProperty long                     newCount;
    @JsonProperty Map<Long, User>          users;
}
