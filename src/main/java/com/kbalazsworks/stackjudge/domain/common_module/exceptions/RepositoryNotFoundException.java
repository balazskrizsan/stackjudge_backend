package com.kbalazsworks.stackjudge.domain.common_module.exceptions;

public class RepositoryNotFoundException extends HttpException
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
