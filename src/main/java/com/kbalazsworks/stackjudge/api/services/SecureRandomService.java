package com.kbalazsworks.stackjudge.api.services;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class SecureRandomService
{
    // @todo: test
    public String get(int length)
    {
        SecureRandom sr   = new SecureRandom();
        byte[]       code = new byte[length];
        sr.nextBytes(code);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(code);
    }
}
