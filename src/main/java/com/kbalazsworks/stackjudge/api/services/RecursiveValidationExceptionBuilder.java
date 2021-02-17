package com.kbalazsworks.stackjudge.api.services;

import javax.validation.ValidationException;
import java.util.List;

public class RecursiveValidationExceptionBuilder
{
    public void buildAndThrow(List<ValidationException> exceptions)
    {
        if (exceptions.isEmpty())
        {
            return;
        }

        ValidationException lastException = exceptions.get(exceptions.size() - 1);
        exceptions.remove(exceptions.size() - 1);

        throw recursiveBuilder(exceptions, lastException);
    }

    private ValidationException recursiveBuilder(
        List<ValidationException> exceptions,
        ValidationException lastException
    )
    {
        if (exceptions.isEmpty())
        {
            return lastException;
        }

        ValidationException newLastException = new ValidationException(exceptions.get(exceptions.size() - 1)
                                                                           .getMessage(), lastException);
        exceptions.remove(exceptions.size() - 1);

        return newLastException;
    }
}
