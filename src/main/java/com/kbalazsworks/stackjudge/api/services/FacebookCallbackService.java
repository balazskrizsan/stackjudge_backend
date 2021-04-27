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
import com.kbalazsworks.stackjudge.state.entities.User;
import com.kbalazsworks.stackjudge.state.repositories.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class FacebookCallbackService
{
    private static final String NETWORK_NAME           = "Facebook";
    private static final String PROTECTED_RESOURCE_URL = "https://graph.facebook.com/v3.2/me";

    private UsersRepository userRepository; //@todo: replace with service
    private JwtService      jwtService;

    @Autowired
    public void setUsersRepository(UsersRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setJwtService(JwtService jwtService)
    {
        this.jwtService = jwtService;
    }

    @Transactional
    public String handler(String code) throws Exception
    {
        final String clientId     = "149500276863432"; //@todo: remove and change
        final String clientSecret = "4738a5809b56a5a2087fb923958fdd4d"; //@todo: remove and change
        final OAuth20Service service = new ServiceBuilder(clientId)
            .apiSecret(clientSecret)
            .callback("https://localhost:8181/account/facebook-callback")
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

        OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
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
