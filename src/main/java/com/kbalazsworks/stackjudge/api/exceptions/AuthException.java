package com.kbalazsworks.stackjudge.api.exceptions;

import com.kbalazsworks.stackjudge.domain.common_module.exceptions.HttpException;

public class AuthException extends HttpException
{
    public AuthException()
    {
        super("Unexpected authentication problem");
    }
}
