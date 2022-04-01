package com.kbalazsworks.stackjudge.api.config;

public class SecurityConstants
{
    public static final String SECRET                     = "SecretKeyToGenJWTs";
    public static final long   EXPIRATION_TIME            = 864_000_000; // 10 days
    public static final String BEARER_TOKEN_PREFIX        = "Bearer_";
    public static final String AUTHENTICATION_COOKIE_NAME = "Authorization";
    public static final String SIGN_UP_URL                = "/account/sign-up";
}
