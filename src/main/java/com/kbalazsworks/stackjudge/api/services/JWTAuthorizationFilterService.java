package com.kbalazsworks.stackjudge.api.services;

import com.kbalazsworks.simple_oidc.entities.BasicAuth;
import com.kbalazsworks.simple_oidc.entities.IntrospectRawResponse;
import com.kbalazsworks.simple_oidc.exceptions.OidcApiException;
import com.kbalazsworks.simple_oidc.services.ICommunicationService;
import com.kbalazsworks.stackjudge.state.entities.User;
import com.kbalazsworks.stackjudge.state.exceptions.StateException;
import com.kbalazsworks.stackjudge.state.services.AccountService;
import lombok.NonNull;
import lombok.SneakyThrows;
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

import static com.kbalazsworks.stackjudge.api.config.SecurityConstants.AUTHENTICATION_ACCESS_TOKEN_HEADER_NAME;
import static com.kbalazsworks.stackjudge.api.config.SecurityConstants.BEARER_TOKEN_PREFIX;

@Slf4j
public class JWTAuthorizationFilterService extends BasicAuthenticationFilter
{
    private final AccountService        accountService;
    private final ICommunicationService communicationService;

    public JWTAuthorizationFilterService(
        @NonNull AuthenticationManager authManager,
        @NonNull AccountService accountService,
        @NonNull ICommunicationService communicationService
    )
    {
        super(authManager);
        this.accountService       = accountService;
        this.communicationService = communicationService;
    }

    @SneakyThrows
    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest req,
        @NonNull HttpServletResponse res,
        @NonNull FilterChain chain
    )
    {
        String prefixedReferenceToken = req.getHeader(AUTHENTICATION_ACCESS_TOKEN_HEADER_NAME);
        if (null == prefixedReferenceToken)
        {
            continueWithoutAuth(chain, req, res);

            return;
        }

        String accessToken = prefixedReferenceToken.replace(BEARER_TOKEN_PREFIX, "");

        IntrospectRawResponse introspectRawResponse;
        try
        {
            introspectRawResponse = communicationService.callIntrospectEndpoint(
                accessToken,
                new BasicAuth("sj.resource.frontend", "sj.resource.frontend")
            );

            if (null == introspectRawResponse.getSub())
            {
                throw new OidcApiException();
            }
        }
        catch (OidcApiException oae)
        {
            // @todo: expired token should send back 401
            log.error("IdeintityServer: Invalid access token: {}, {}", oae.getMessage(), accessToken);

            continueWithoutAuth(chain, req, res);

            return;
        }

        String idsUserId = introspectRawResponse.getSub();
        User   user;
        try
        {
            user = accountService.get(idsUserId);
        }
        catch (StateException se)
        {
            user = new User(idsUserId);

            accountService.createUser(user);
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, "random");

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
}
