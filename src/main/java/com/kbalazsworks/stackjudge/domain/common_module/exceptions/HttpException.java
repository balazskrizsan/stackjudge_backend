package com.kbalazsworks.stackjudge.domain.common_module.exceptions;

import org.springframework.http.HttpStatus;

public abstract class HttpException extends RuntimeException
{
    private int        errorCode    = 100;
    private HttpStatus statusCode   = HttpStatus.BAD_REQUEST;
    private boolean    printTrace   = false;

    public HttpException()
    {
        super();
    }

    public HttpException(String message)
    {
        super(message);
    }

    public int getErrorCode()
    {
        return errorCode;
    }

    public HttpStatus getStatusCode()
    {
        return statusCode;
    }

    public boolean isPrintTrace()
    {
        return printTrace;
    }

    public HttpException withErrorCode(int errorCode)
    {
        this.errorCode = errorCode;

        return this;
    }

    public HttpException withStatusCode(HttpStatus statusCode)
    {
        this.statusCode = statusCode;

        return this;
    }

    public HttpException withPrintTrace(boolean printTrace)
    {
        this.printTrace = printTrace;

        return this;
    }
}
