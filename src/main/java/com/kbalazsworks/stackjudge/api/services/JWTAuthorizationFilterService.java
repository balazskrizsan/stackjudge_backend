package com.kbalazsworks.stackjudge.api.services;

import com.kbalazsworks.stackjudge.state.entities.User;
import com.kbalazsworks.stackjudge.state.services.AccountService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static com.kbalazsworks.stackjudge.api.config.SecurityConstants.AUTHENTICATION_COOKIE_NAME;
import static com.kbalazsworks.stackjudge.api.config.SecurityConstants.BEARER_TOKEN_PREFIX;

@Slf4j
public class JWTAuthorizationFilterService extends BasicAuthenticationFilter
{
    private final AccountService accountService;
    private final JwtService     jwtService;

    public JWTAuthorizationFilterService(
        @NonNull AuthenticationManager authManager,
        @NonNull AccountService accountService,
        @NonNull JwtService jwtService
    )
    {
        super(authManager);
        this.accountService = accountService;
        this.jwtService     = jwtService;
    }

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest req,
        @NonNull HttpServletResponse res,
        @NonNull FilterChain chain
    ) throws IOException, ServletException
    {
        if (req.getCookies() == null)
        {
            continueWithoutAuth(chain, req, res);

            return;
        }

        // @todo: check cookie for HttpOnly=true and Secure=true
        Optional<Cookie> optionalToken = Arrays
            .stream(req.getCookies())
            .filter(c -> c.getName().equals(AUTHENTICATION_COOKIE_NAME))
            .findFirst();

        if (!optionalToken.isPresent())
        {
            continueWithoutAuth(chain, req, res);

            return;
        }

        // @todo: remove cookie if invalid

        UsernamePasswordAuthenticationToken authentication = getAuthentication(
            optionalToken.get().getValue().replace(BEARER_TOKEN_PREFIX, "")
        );

        // WebSecurityConfig/SessionCreationPolicy.NEVER to disable the redis save
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(req, res);
    }

    private void continueWithoutAuth(
        @NonNull FilterChain chain,
        @NonNull HttpServletRequest req,
        @NonNull HttpServletResponse res
    ) throws ServletException, IOException
    {
        SecurityContextHolder.getContext().setAuthentication(null);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(@NonNull String token)
    {
        if (jwtService.isValid(token))
        {
            User user = accountService.findById(jwtService.getUserId(token));

            return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
        }

        return null;
    }
}
