package com.kbalazsworks.stackjudge.oidc.exceptions;

import lombok.experimental.StandardException;

@StandardException
public class OidcJwksVerificationException extends OidcException
{
    public OidcJwksVerificationException()
    {
    }

    public OidcJwksVerificationException(String message)
    {
        super(message);
    }
}
