package com.kbalazsworks.stackjudge.api.services;

import com.kbalazsworks.stackjudge.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.stackjudge.api.exceptions.ApiException;
import com.kbalazsworks.stackjudge.api.value_objects.ResponseData;
import com.kbalazsworks.stackjudge.domain.exceptions.HttpException;
import com.kbalazsworks.stackjudge.domain.exceptions.RepositoryNotFoundException;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;

@ControllerAdvice
public class RestResponseExceptionService extends ResponseEntityExceptionHandler
{
    @Override
    protected @NonNull ResponseEntity<Object> handleHttpRequestMethodNotSupported(
        HttpRequestMethodNotSupportedException e,
        @NonNull HttpHeaders headers,
        @NonNull HttpStatus status,
        @NonNull WebRequest request
    )
    {
        ResponseEntityBuilder<Object> errorResponseEntityBuilder = new ResponseEntityBuilder<>();
        errorResponseEntityBuilder.data(e.getMessage());

        logger.error(e.getMessage());
        try
        {
            return (ResponseEntity) errorResponseEntityBuilder.build();
        }
        catch (ApiException apiException)
        {
            apiException.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {HttpException.class})
    protected ResponseEntity<ResponseData<String>> handleConflict(HttpException e, WebRequest request) throws Exception
    {
        ResponseEntityBuilder<String> errorResponseEntityBuilder = new ResponseEntityBuilder<String>()
            .data(e.getMessage())
            .errorCode(e.getErrorCode())
            .statusCode(e.getStatusCode());

        logger.error(e.getMessage());
        if (e.isPrintTrace())
        {
            e.printStackTrace();
        }

        return errorResponseEntityBuilder.build();
    }

    @ExceptionHandler(value = {RuntimeException.class})
    protected ResponseEntity<ResponseData<String>> handleConflict(RuntimeException e, WebRequest request)
        throws Exception
    {
        return exceptionHandler(e);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<ResponseData<String>> handleConflict(Exception e, WebRequest request) throws Exception
    {
        return exceptionHandler(e);
    }

    private ResponseEntity<ResponseData<String>> exceptionHandler(Exception e) throws ApiException
    {
        ResponseEntityBuilder<String> errorResponseEntityBuilder = new ResponseEntityBuilder<String>()
            .data(getErrorMessageAndLog(e))
            .errorCode(getErrorCode(e))
            .statusCode(getHttpStatus(e));

        return errorResponseEntityBuilder.build();
    }

    private HttpStatus getHttpStatus(Throwable e)
    {
        if (e instanceof ValidationException)
        {
            return HttpStatus.BAD_REQUEST;
        }

        if (e instanceof RepositoryNotFoundException)
        {
            return HttpStatus.NOT_FOUND;
        }

        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    private String getErrorMessageAndLog(Throwable e)
    {
        if (e instanceof ValidationException)
        {
            while (e != null)
            {
                logger.error(e.getMessage());
                e = e.getCause();
            }

            return "Validation error.";
        }

        logger.error(e.getMessage());

        return "Unknown error occurred.";
    }

    private int getErrorCode(Throwable e)
    {
        if (e instanceof ValidationException)
        {
            return 2;
        }

        return 1;
    }
}
