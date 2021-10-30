package com.kbalazsworks.stackjudge.api.services;

import org.springframework.stereotype.Service;

@Service
public class FrontendUriService
{
    public String getAccountLoginJwtUrl(String jwt)
    {
        return "account/login/".concat(jwt);
    }

    public String getAccountLoginErrorUrl()
    {
        return "account/login-error/";
    }
}
