package com.kbalazsworks.stackjudge.api.services;

import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class SpringCookieService
{
    public void setCookie(HttpServletResponse response, Cookie cookie)
    {
        StringBuffer cookieString = new StringBuffer();

        cookieString.append(cookie.getName());
        cookieString.append("=");
        cookieString.append(cookie.getValue());
        cookieString.append("; ");

        if (cookie.getPath() != null)
        {
            cookieString.append("Path=");
            cookieString.append(cookie.getPath());
            cookieString.append("; ");
        }

        if (cookie.getSecure())
        {
            cookieString.append("Secure; ");
        }

        if (cookie.isHttpOnly())
        {
            cookieString.append("HttpOnly; ");
        }

        if (cookie.getMaxAge() != -1)
        {
            cookieString.append("max-age=");
            cookieString.append(cookie.getMaxAge());
            cookieString.append(";");
        }

        response.setHeader("Set-Cookie", cookieString.toString());
    }
}
