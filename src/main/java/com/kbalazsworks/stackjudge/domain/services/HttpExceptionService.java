package com.kbalazsworks.stackjudge.domain.services;

import com.kbalazsworks.stackjudge.domain.exceptions.CompanyHttpException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class HttpExceptionService
{
    public void throwCompanyOwnRequestAlreadySent()
    {
        throwHttpException("Company own request already sent in the last 24 hours.", 1004);
    }

    public void throwCompanyOwnCompleteRequestFailed()
    {
        throwHttpException("Company own complete request failed.", 1005);
    }

    public void throwCompanyAlreadyOwnedByTheUser()
    {
        throwHttpException("Company already owned by the user.", 1006);
    }

    private void throwHttpException(String message, int errorCode, HttpStatus statusCode)
    {
        throw new CompanyHttpException(message).withErrorCode(errorCode).withStatusCode(statusCode);
    }

    private void throwHttpException(String message, int errorCode)
    {
        throwHttpException(message, errorCode, HttpStatus.BAD_REQUEST);
    }

    public void throwCompanyOwnRequestFailed()
    {
        throwHttpException("Company own request failed.", 1003);
    }
}
