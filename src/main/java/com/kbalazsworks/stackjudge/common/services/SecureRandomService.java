package com.kbalazsworks.stackjudge.common.services;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class SecureRandomService
{
    public String get(int length)
    {
        return RandomStringUtils.random(length, 0, 0, true, true, null, new SecureRandom());
    }

    public String getUrlEncoded(int length)
    {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(get(length).getBytes());
    }
}
