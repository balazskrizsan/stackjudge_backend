package com.kbalazsworks.stackjudge.fake_builders;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kbalazsworks.stackjudge_aws_sdk.common.entities.StdResponse;
import com.kbalazsworks.stackjudge_aws_sdk.schema_parameter_objects.PutAndSaveResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@Accessors(fluent = true)
@Getter
@Setter
public class StdResponsePutAndSaveResponseFakeBuilder
{
    @JsonProperty("status")
    private HttpStatus         status             = HttpStatus.OK;
    @JsonProperty("headers")
    private HttpHeaders        headers            = new HttpHeaders();
    @JsonProperty("data")
    private PutAndSaveResponse putAndSaveResponse = new PutAndSaveResponseFakeBuilder().build();

    public StdResponse<PutAndSaveResponse> build()
    {
        return new StdResponse<>(status, headers, putAndSaveResponse);
    }
}
