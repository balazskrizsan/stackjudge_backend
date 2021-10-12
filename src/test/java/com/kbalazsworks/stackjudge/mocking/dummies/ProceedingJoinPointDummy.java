package com.kbalazsworks.stackjudge.mocking.dummies;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.SourceLocation;
import org.aspectj.runtime.internal.AroundClosure;

public class ProceedingJoinPointDummy implements ProceedingJoinPoint
{
    @Override public void set$AroundClosure(AroundClosure arc)
    {
    }

    @Override public Object proceed() throws Throwable
    {
        return null;
    }

    @Override public Object proceed(Object[] args) throws Throwable
    {
        return null;
    }

    @Override public String toShortString()
    {
        return null;
    }

    @Override public String toLongString()
    {
        return null;
    }

    @Override public Object getThis()
    {
        return null;
    }

    @Override public Object getTarget()
    {
        return null;
    }

    @Override public Object[] getArgs()
    {
        return new Object[0];
    }

    @Override public Signature getSignature()
    {
        return null;
    }

    @Override public SourceLocation getSourceLocation()
    {
        return null;
    }

    @Override public String getKind()
    {
        return null;
    }

    @Override public StaticPart getStaticPart()
    {
        return null;
    }
}
