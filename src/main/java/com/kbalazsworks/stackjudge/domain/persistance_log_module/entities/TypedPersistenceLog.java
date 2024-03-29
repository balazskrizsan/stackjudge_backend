package com.kbalazsworks.stackjudge.domain.persistance_log_module.entities;

import com.kbalazsworks.stackjudge.domain.persistance_log_module.enums.PersistenceLogTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.codehaus.jackson.annotate.JsonProperty;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
// @todo: convert to record, but JOOQ-JSONP has same problem with that
public class TypedPersistenceLog<T>
{
    @JsonProperty private final Long                   id;
    @JsonProperty private final PersistenceLogTypeEnum type;
    @JsonProperty private final T                      data;
    @JsonProperty private final LocalDateTime          createdAt;
}
