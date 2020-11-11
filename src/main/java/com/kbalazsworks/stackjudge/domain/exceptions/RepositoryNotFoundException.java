package com.kbalazsworks.stackjudge.domain.exceptions;

public class RepositoryNotFoundException extends RuntimeException
{
    public RepositoryNotFoundException()
    {
        super();
    }

    public RepositoryNotFoundException(String message)
    {
        super(message);
    }
}
