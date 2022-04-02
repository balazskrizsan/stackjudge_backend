package com.kbalazsworks.stackjudge.api.services;

import org.springframework.stereotype.Service;

@Service
public class FrontendUriService
{
    public String getAccountLoginUrl()
    {
        return "account/login";
    }

    public String getAccountLoginErrorUrl()
    {
        return "account/login-error";
    }
}
