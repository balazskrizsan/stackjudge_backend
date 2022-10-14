package com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.ids._entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.processing.Generated;

@Generated("OpenSDK: https://github.com/balazskrizsan/OpenSdk")
@Jacksonized
@Builder(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Getter
@ToString
public final class ApiResponseDataIdsServiceAccountListResponse
{
    @JsonProperty("data")
    private final IdsServiceAccountListResponse idsServiceAccountListResponse;
    @JsonProperty("success")
    private final Boolean                       success;
    @JsonProperty("errorCode")
    private final Integer                       errorCode;
    @JsonProperty("requestId")
    private final String                        requestId;
}
