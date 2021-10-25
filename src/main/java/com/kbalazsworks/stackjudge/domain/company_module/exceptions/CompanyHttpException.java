package com.kbalazsworks.stackjudge.domain.company_module.exceptions;

import com.kbalazsworks.stackjudge.domain.common_module.exceptions.HttpException;

public class CompanyHttpException extends HttpException
{
    public CompanyHttpException(String message)
    {
        super(message);
    }
}
