package com.kbalazsworks.stackjudge.domain.common_module.exceptions;

import lombok.NonNull;

public class PebbleException extends Exception
{
    public PebbleException(@NonNull String message)
    {
        super(message);
    }
}
