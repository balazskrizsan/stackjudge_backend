package com.kbalazsworks.stackjudge.domain.address_module.exceptions;

import com.kbalazsworks.stackjudge.domain.common_module.exceptions.HttpException;

public class AddressHttpException extends HttpException
{
    public AddressHttpException(String message)
    {
        super(message);
    }
}
