package com.kbalazsworks.stackjudge.oidc.exceptions;

import lombok.experimental.StandardException;

@StandardException
public class OidcJwtParseException extends OidcException
{
    public OidcJwtParseException()
    {
    }

    public OidcJwtParseException(String message)
    {
        super(message);
    }
}
