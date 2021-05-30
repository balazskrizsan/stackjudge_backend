package com.kbalazsworks.stackjudge.api.services.facebook_callback_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.kbalazsworks.stackjudge.api.exceptions.AuthException;
import com.kbalazsworks.stackjudge.api.value_objects.FacebookUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class GetJwtLoginUrlService
{
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

    public OAuth2AccessToken getAccessToken(OAuth20Service service, String code) throws AuthException
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
