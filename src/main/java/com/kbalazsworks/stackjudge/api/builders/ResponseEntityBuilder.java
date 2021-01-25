package com.kbalazsworks.stackjudge.api.builders;

import com.kbalazsworks.stackjudge.api.exceptions.ApiException;
import com.kbalazsworks.stackjudge.api.value_objects.ResponseData;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

public class ResponseEntityBuilder<T>
{
    private T           data;
    private int         errorCode  = 0;
    private HttpStatus  statusCode = HttpStatus.OK;
    private HttpHeaders headers    = new HttpHeaders();

    public ResponseEntity<ResponseData<T>> build() throws ApiException
    {
        Boolean success = errorCode == 0;

        if (errorCode > 0 && statusCode == HttpStatus.OK)
        {
            throw new ApiException("Status code setup is needed for error response");
        }

        ResponseData<T> responseData = new ResponseData<>(data, success, errorCode, "1");

        return new ResponseEntity<>(responseData, headers, statusCode);
    }

    public void downloadAsCsv(String fileName)
    {
        headers.setAccessControlExposeHeaders(Collections.singletonList("Content-Disposition"));
        headers.set("Content-Disposition", "attachment; filename=" + fileName + ".csv");
    }

    public T getData()
    {
        return data;
    }

    public ResponseEntityBuilder<T> setData(T data)
    {
        this.data = data;

        return this;
    }

    public int getErrorCode()
    {
        return errorCode;
    }

    public ResponseEntityBuilder<T> setErrorCode(int errorCode)
    {
        this.errorCode = errorCode;

        return this;
    }

    public HttpStatus getStatusCode()
    {
        return statusCode;
    }

    public ResponseEntityBuilder<T> setStatusCode(HttpStatus statusCode)
    {
        this.statusCode = statusCode;

        return this;
    }
}
