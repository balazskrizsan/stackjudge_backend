package com.kbalazsworks.stackjudge.api.value_objects;

import io.swagger.annotations.ApiModelProperty;

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

    public ResponseData(T data, Boolean success, int errorCode, String requestId)
    {
        this.data = data;
        this.success = success;
        this.errorCode = errorCode;
        this.requestId = requestId;
    }

    public T getData()
    {
        return data;
    }

    public Boolean getSuccess()
    {
        return success;
    }

    public int getErrorCode()
    {
        return errorCode;
    }

    public String getRequestId()
    {
        return requestId;
    }
}
