package com.kbalazsworks.stackjudge.api.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.kbalazsworks.stackjudge.api.builders.OAuthFacebookServiceBuilder;
import com.kbalazsworks.stackjudge.api.exceptions.AuthException;
import com.kbalazsworks.stackjudge.api.value_objects.FacebookUser;
import com.kbalazsworks.stackjudge.state.entities.User;
import com.kbalazsworks.stackjudge.state.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
@RequiredArgsConstructor
public class FacebookCallbackService
{
    private final UserService                 userService;
    private final JwtService                  jwtService;
    private final OAuthFacebookServiceBuilder oAuthFacebookServiceBuilder;

    private static final String FACEBOOK_GRAPH_API = "https://graph.facebook.com/v3.2/me";

    @Transactional
    public String handler(String code) throws AuthException, IOException, ExecutionException, InterruptedException
    {
        OAuth20Service service = oAuthFacebookServiceBuilder.create();
        OAuth2AccessToken accessToken = getAccessToken(service, code);

        OAuthRequest request = new OAuthRequest(Verb.GET, FACEBOOK_GRAPH_API);
        request.addParameter("fields", "id,name");

        service.signRequest(accessToken, request);

        Response     response     = service.execute(request);
        ObjectMapper objectMapper = new ObjectMapper();
        FacebookUser facebookUser = objectMapper.readValue(response.getBody(), FacebookUser.class);

        User user = userService.findByFacebookId(facebookUser.id());
        if (null != user)
        {
            userService.updateFacebookAccessToken(accessToken.getAccessToken(), facebookUser.id());

            return jwtService.generateAccessToken(user);
        }

        user = userService.create(new User(
            null,
            facebookUser.name(),
            "random",
            accessToken.getAccessToken(),
            facebookUser.id()
        ));

        return jwtService.generateAccessToken(user);
    }

    private OAuth2AccessToken getAccessToken(OAuth20Service service, String code) throws AuthException
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
