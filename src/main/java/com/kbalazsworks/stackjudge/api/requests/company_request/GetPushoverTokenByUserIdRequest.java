package com.kbalazsworks.stackjudge.api.requests.company_request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class GetPushoverTokenByUserIdRequest
{
    @Size(min = 36, max = 36)
    private String idsUserId;
}
