package com.kbalazsworks.stackjudge.oidc.exceptions;

import lombok.experimental.StandardException;

@StandardException
public class OidcException extends Exception
{
    public OidcException()
    {
    }

    public OidcException(String message)
    {
        super(message);
    }
}
