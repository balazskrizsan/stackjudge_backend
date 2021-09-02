package com.kbalazsworks.stackjudge.domain.services;

import com.kbalazsworks.stackjudge.domain.exceptions.CompanyHttpException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

// @test
@Service
public class HttpExceptionService
{
    private void throwHttpException(String message, int errorCode, HttpStatus statusCode)
    {
        throw new CompanyHttpException(message).withErrorCode(errorCode).withStatusCode(statusCode);
    }

    private void throwHttpException(String message, int errorCode)
    {
        throwHttpException(message, errorCode, HttpStatus.BAD_REQUEST);
    }

    public void throwCompanyOwnRequestAlreadySent()
    {
        throwHttpException("Company own request already sent in the last 24 hours.", 1004);
    }

    public void throwCompanyOwnRequestFailed()
    {
        throwHttpException("Company own request failed.", 1003);
    }
}
