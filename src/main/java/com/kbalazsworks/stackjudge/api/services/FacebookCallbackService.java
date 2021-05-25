package com.kbalazsworks.stackjudge.api.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.apis.FacebookApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.kbalazsworks.stackjudge.api.controllers.account_controller.FacebookUser;
import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import com.kbalazsworks.stackjudge.state.entities.User;
import com.kbalazsworks.stackjudge.state.repositories.UsersRepository;
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
    private final UsersRepository       userRepository; //@todo: replace with service
    private final JwtService            jwtService;
    private final ApplicationProperties applicationProperties;

    private static final String FACEBOOK_GRAPH_API = "https://graph.facebook.com/v3.2/me";

    @Transactional
    public String handler(String code) throws Exception
    {
        final OAuth20Service service = new ServiceBuilder(applicationProperties.getFacebookClientId())
            .apiSecret(applicationProperties.getFacebookClientSecret())
            .callback(applicationProperties.getFacebookCallbackUrl())
            .build(FacebookApi.instance());

        final OAuth2AccessToken accessToken;
        try
        {
            accessToken = service.getAccessToken(code);
        }
        catch (IOException e)
        {
            log.error("Facebook IO error: ".concat(e.getMessage()));
            throw new Exception(""); //@todo: do with http exception
        }
        catch (InterruptedException e)
        {
            log.error("Facebook Interrupted error: ".concat(e.getMessage()));
            throw new Exception(""); //@todo: do with http exception
        }
        catch (ExecutionException e)
        {
            log.error("Facebook Execution error: ".concat(e.getMessage()));
            throw new Exception(""); //@todo: do with http exception
        }

        OAuthRequest request = new OAuthRequest(Verb.GET, FACEBOOK_GRAPH_API);
        request.addParameter("fields", "id,name");

        service.signRequest(accessToken, request);

        Response     response     = service.execute(request);
        ObjectMapper objectMapper = new ObjectMapper();
        FacebookUser facebookUser = objectMapper.readValue(response.getBody(), FacebookUser.class);

        User user = userRepository.findByFacebookId(facebookUser.id());
        if (null != user)
        {
            userRepository.updateFacebookAccessToken(accessToken.getAccessToken(), facebookUser.id());

            return jwtService.generateAccessToken(user);
        }

        user = userRepository.save(new User(
            null,
            facebookUser.name(),
            "random",
            accessToken.getAccessToken(),
            facebookUser.id()
        ));

        return jwtService.generateAccessToken(user);
    }
}
