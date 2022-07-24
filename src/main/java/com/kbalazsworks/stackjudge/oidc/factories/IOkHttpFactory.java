package com.kbalazsworks.stackjudge.oidc.factories;

import okhttp3.OkHttpClient;

public interface IOkHttpFactory
{
    OkHttpClient createOkHttpClient();
}
