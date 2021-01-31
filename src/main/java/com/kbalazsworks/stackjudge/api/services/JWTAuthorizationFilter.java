package com.kbalazsworks.stackjudge.api.services;

import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static com.kbalazsworks.stackjudge.api.config.SecurityConstants.*;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter
{
    private static final Logger logger = LoggerFactory.getLogger(JWTAuthorizationFilter.class);

    public JWTAuthorizationFilter(AuthenticationManager authManager)
    {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest req,
        HttpServletResponse res,
        FilterChain chain
    ) throws IOException, ServletException
    {
        String header = req.getHeader(HEADER_STRING);
        if (header == null || !header.startsWith(TOKEN_PREFIX))
        {
            logger.warn("JWT Auth error: header null or missing.");
            SecurityContextHolder.getContext().setAuthentication(null);
            chain.doFilter(req, res);

            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request)
    {
        String token = request.getHeader(HEADER_STRING);
        if (token != null)
        {
            String user = Jwts
                .parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody()
                .getSubject();

            if (user != null)
            {
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }
            logger.warn("JWT Auth error: user is null.");

            return null;
        }
        logger.warn("JWT Auth error: token is null.");

        return null;
    }
}
