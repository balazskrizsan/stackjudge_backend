package com.kbalazsworks.stackjudge.mocking.dummies;

import org.aspectj.lang.Signature;

public class SignatureDummy implements Signature
{
    @Override public String toShortString()
    {
        return null;
    }

    @Override public String toLongString()
    {
        return null;
    }

    @Override public String getName()
    {
        return null;
    }

    @Override public int getModifiers()
    {
        return 0;
    }

    @Override public Class getDeclaringType()
    {
        return null;
    }

    @Override public String getDeclaringTypeName()
    {
        return null;
    }

    public String toString()
    {
        return "dummy string answer";
    }
}
