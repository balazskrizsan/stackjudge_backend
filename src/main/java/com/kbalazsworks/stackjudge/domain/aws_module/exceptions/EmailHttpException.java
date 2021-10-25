package com.kbalazsworks.stackjudge.domain.aws_module.exceptions;

import com.kbalazsworks.stackjudge.domain.common_module.exceptions.HttpException;

public class EmailHttpException extends HttpException
{
    public EmailHttpException(String message)
    {
        super(message);
    }
}
