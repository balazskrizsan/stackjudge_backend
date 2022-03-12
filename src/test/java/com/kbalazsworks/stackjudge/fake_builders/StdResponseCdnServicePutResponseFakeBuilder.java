package com.kbalazsworks.stackjudge.fake_builders;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbalazsworks.stackjudge_aws_sdk.common.entities.StdResponse;
import com.kbalazsworks.stackjudge_aws_sdk.schema_parameter_objects.CdnServicePutResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@Accessors(fluent = true)
@Getter
@Setter
public class StdResponseCdnServicePutResponseFakeBuilder
{
    @JsonProperty("status")
    private HttpStatus            status  = HttpStatus.OK;
    @JsonProperty("headers")
    private HttpHeaders           headers = new HttpHeaders();
    @JsonProperty("data")
    private CdnServicePutResponse cdnServicePutResponse    = new CdnServicePutResponseFakeBuilder().build();

    public StdResponse<CdnServicePutResponse> build()
    {
        return new StdResponse<>(status, headers, cdnServicePutResponse);
    }
}
