package com.kbalazsworks.stackjudge.api.services;

import org.springframework.stereotype.Service;

@Service
public class FrontendUriService
{
    public String getAccountLoginJwt(String jwt)
    {
        return "account/login/".concat(jwt);
    }
}
