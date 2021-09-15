package com.kbalazsworks.stackjudge.api.requests.company_request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class GetOwnCompleteRequest
{
    @JsonProperty("code")
    @Size(min = 30, max = 60)
    String code;
}
