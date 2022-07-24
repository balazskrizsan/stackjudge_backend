package com.kbalazsworks.stackjudge.oidc.exceptions;

import lombok.experimental.StandardException;

@StandardException
public class OidcKeyException extends OidcException
{
    public OidcKeyException(String message)
    {
        super(message);
    }
}
