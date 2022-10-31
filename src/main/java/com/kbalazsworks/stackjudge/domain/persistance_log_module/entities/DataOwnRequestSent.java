package com.kbalazsworks.stackjudge.domain.persistance_log_module.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonProperty;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DataOwnRequestSent
{
    @JsonProperty("requesterIdsUserId") private String requesterOwnerId;
    @JsonProperty("requestedCompanyId") private long requestedCompanyId;
}
