package com.kbalazsworks.stackjudge.api.services.facebook_services.scribe_java_facebook_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.kbalazsworks.stackjudge.api.exceptions.AuthException;
import com.kbalazsworks.stackjudge.api.services.FrontendUriService;
import com.kbalazsworks.stackjudge.api.services.JwtService;
import com.kbalazsworks.stackjudge.api.value_objects.FacebookUser;
import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
@RequiredArgsConstructor
public class GetFacebookUserWithTokenFromCodeService
{
    private final ApplicationProperties applicationProperties;
    private final JwtService            jwtService;
    private final FrontendUriService    frontendUriService;

    public FacebookUser getFacebookUser(Response response)
    {
        ObjectMapper objectMapper = new ObjectMapper();
        try
        {
            return objectMapper.readValue(response.getBody(), FacebookUser.class);
        }
        catch (IOException e)
        {
            log.error(e.getMessage());

            throw new AuthException();
        }
    }

    public Response getFacebookResponse(OAuth20Service service, OAuthRequest request)
    {
        try
        {
            return service.execute(request);
        }
        catch (InterruptedException | ExecutionException | IOException e)
        {
            log.error(e.getMessage());
        }

        throw new AuthException();
    }

    public OAuth2AccessToken getAccessToken(@NonNull OAuth20Service service, @NonNull String code) throws AuthException
    {
        try
        {
            return service.getAccessToken(code);
        }
        catch (IOException e)
        {
            log.error("Facebook IO error: ".concat(e.getMessage()));
        }
        catch (InterruptedException e)
        {
            log.error("Facebook Interrupted error: ".concat(e.getMessage()));
        }
        catch (ExecutionException e)
        {
            log.error("Facebook Execution error: ".concat(e.getMessage()));
        }

        throw new AuthException();
    }
}
