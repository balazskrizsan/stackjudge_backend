package com.kbalazsworks.stackjudge.api.services;

import com.kbalazsworks.stackjudge.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.stackjudge.api.exceptions.ApiException;
import com.kbalazsworks.stackjudge.api.value_objects.ResponseData;
import com.kbalazsworks.stackjudge.domain.exceptions.RepositoryNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;

@ControllerAdvice
public class RestResponseEntityExceptionService extends ResponseEntityExceptionHandler
{
    private static final Logger logger = LoggerFactory.getLogger(RestResponseEntityExceptionService.class);

    @ExceptionHandler(value = {RuntimeException.class})
    protected ResponseEntity<ResponseData<String>> handleConflict(RuntimeException e, WebRequest request) throws Exception
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
        ResponseEntityBuilder<String> errorResponseEntityBuilder = new ResponseEntityBuilder<>();
        errorResponseEntityBuilder.setData(getErrorMessage(e));
        errorResponseEntityBuilder.setErrorCode(getErrorCode(e));
        errorResponseEntityBuilder.setStatusCode(getHttpStatus(e));

        logger.error(e.getMessage());
        if (isTraceNeeded(e))
        {
            e.printStackTrace();
        }

        return errorResponseEntityBuilder.build();
    }

    private boolean isTraceNeeded(Exception e)
    {
        return !(
            (e instanceof ValidationException)
            || (e instanceof RepositoryNotFoundException));
    }

    private HttpStatus getHttpStatus(Exception e)
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

    private String getErrorMessage(Exception e)
    {
        if (e instanceof ValidationException)
        {
            return "Validation error";
        }

        if (e instanceof RepositoryNotFoundException)
        {
            return "Not found";
        }

        return "Unknown error occurred";
    }

    private int getErrorCode(Exception e)
    {
        if (e instanceof ValidationException)
        {
            return 2;
        }

        return 1;
    }
}
