package com.kbalazsworks.stackjudge.api.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
@Log4j2
public class SpringCookieService
{
    public void set(HttpServletResponse response, Cookie cookie)
    {
        StringBuffer cookieString = new StringBuffer();

        cookieString.append(cookie.getName());
        cookieString.append("=");
        cookieString.append(cookie.getValue());
        cookieString.append(";");

        if (cookie.getPath() != null)
        {
            cookieString.append(" Path=");
            cookieString.append(cookie.getPath());
            cookieString.append(";");
        }

        if (cookie.getSecure())
        {
            cookieString.append(" Secure;");
        }

        if (cookie.isHttpOnly())
        {
            cookieString.append(" HttpOnly;");
        }

        if (cookie.getMaxAge() != -1)
        {
            cookieString.append(" Max-Age=");
            cookieString.append(cookie.getMaxAge());
            cookieString.append(";");
        }

        log.info("Cookie created: {}", cookie.getName());

        response.setHeader("Set-Cookie", cookieString.toString());
    }
}
