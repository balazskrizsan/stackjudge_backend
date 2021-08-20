package com.kbalazsworks.stackjudge.api.services;

import com.kbalazsworks.stackjudge.state.entities.User;
import com.kbalazsworks.stackjudge.state.services.AccountService;
import lombok.extern.slf4j.Slf4j;
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

import static com.kbalazsworks.stackjudge.api.config.SecurityConstants.HEADER_STRING;
import static com.kbalazsworks.stackjudge.api.config.SecurityConstants.TOKEN_PREFIX;

@Slf4j
public class JWTAuthorizationFilterService extends BasicAuthenticationFilter
{
    private final AccountService accountService;
    private final JwtService     jwtService;

    public JWTAuthorizationFilterService(
        AuthenticationManager authManager,
        AccountService accountService,
        JwtService jwtService
    )
    {
        super(authManager);
        this.accountService = accountService;
        this.jwtService     = jwtService;
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest req,
        HttpServletResponse res,
        FilterChain chain
    ) throws IOException, ServletException
    {
        String authorizationHeader = req.getHeader(HEADER_STRING);

        if (authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_PREFIX))
        {
            SecurityContextHolder.getContext().setAuthentication(null);
            chain.doFilter(req, res);

            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(
            authorizationHeader.replace(TOKEN_PREFIX, "")
        );
        //@todo: authentication check null
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token)
    {
        if (jwtService.isValid(token))
        {
            User user = accountService.findById(jwtService.getUserId(token));

            return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
        }

        return null;
    }
}
