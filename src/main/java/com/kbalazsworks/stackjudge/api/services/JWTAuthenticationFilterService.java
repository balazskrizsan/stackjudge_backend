package com.kbalazsworks.stackjudge.api.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;

import static com.kbalazsworks.stackjudge.api.config.SecurityConstants.*;

@Slf4j
public class JWTAuthenticationFilterService extends UsernamePasswordAuthenticationFilter
{
    private final AuthenticationManager authenticationManager;

    public JWTAuthenticationFilterService(AuthenticationManager authenticationManager)
    {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
    throws AuthenticationException
    {
        String username = req.getParameter("username");
        log.info("Login attempt with username: " + username);

        return authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, req.getParameter("password"), new ArrayList<>())
        );
    }

    @Override
    protected void successfulAuthentication(
        HttpServletRequest req,
        HttpServletResponse res,
        FilterChain chain,
        Authentication auth
    )
    {
        String token = Jwts
            .builder()
            .setSubject(((User) auth.getPrincipal()).getUsername())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS512, SECRET)
            .compact();

        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }
}
