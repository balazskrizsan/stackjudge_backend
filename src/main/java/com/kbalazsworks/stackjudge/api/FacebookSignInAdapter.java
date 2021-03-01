package com.kbalazsworks.stackjudge.api;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;

@Service
public class FacebookSignInAdapter implements SignInAdapter
{
    @Override
    public String signIn(String localUserId, Connection<?> connection, NativeWebRequest request)
    {
        SecurityContextHolder
            .getContext()
            .setAuthentication(
                new UsernamePasswordAuthenticationToken(
                    connection.getDisplayName(),
                    null,
                    List.of(new SimpleGrantedAuthority("FACEBOOK_USER"))
                )
            );

        return null;
    }
}

