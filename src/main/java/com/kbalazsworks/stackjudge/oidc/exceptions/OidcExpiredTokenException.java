package com.kbalazsworks.stackjudge.oidc.exceptions;

import lombok.experimental.StandardException;

@StandardException
public class OidcExpiredTokenException extends OidcException
{
    public OidcExpiredTokenException()
    {
    }

    public OidcExpiredTokenException(String message)
    {
        super(message);
    }
}
