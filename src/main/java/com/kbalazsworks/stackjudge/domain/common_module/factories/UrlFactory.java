package com.kbalazsworks.stackjudge.domain.common_module.factories;

import com.kbalazsworks.stackjudge.domain.aws_module.exceptions.ContentReadException;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

@Service
public class UrlFactory
{
    public URL create(String url) throws ContentReadException
    {
        try
        {
            return new URL(url);
        }
        catch (MalformedURLException e)
        {
            throw new ContentReadException("UrlFactory content read error: " + e.getMessage());
        }
    }
}
