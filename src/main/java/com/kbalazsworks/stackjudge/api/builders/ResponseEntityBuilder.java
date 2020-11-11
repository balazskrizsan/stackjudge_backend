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

    public void setData(T data)
    {
        this.data = data;
    }

    public int getErrorCode()
    {
        return errorCode;
    }

    public void setErrorCode(int errorCode)
    {
        this.errorCode = errorCode;
    }

    public HttpStatus getStatusCode()
    {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode)
    {
        this.statusCode = statusCode;
    }
}
