package com.kbalazsworks.stackjudge.api.value_objects;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResponseData<T>
{
    @ApiModelProperty(notes = "data", name="data", required=true, value="value")
    private final T       data;
    @ApiModelProperty(notes = "success", name="success", required=true, value="value")
    private final Boolean success;
    @ApiModelProperty(notes = "errorCode", name="errorCode", required=true, value="value")
    private final int     errorCode;
    @ApiModelProperty(notes = "requestId", name="requestId", required=true, value="value")
    private final String  requestId;
}
