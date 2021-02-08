package com.kbalazsworks.stackjudge.api.controllers.account_controller;

import com.github.scribejava.apis.FacebookApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.kbalazsworks.stackjudge.session.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RestController("AccountFacebookCallbackAction")
@RequestMapping(AccountConfig.CONTROLLER_URI)
public class FacebookCallbackController
{
    private static final String                NETWORK_NAME           = "Facebook";
    private static final String                PROTECTED_RESOURCE_URL = "https://graph.facebook.com/v3.2/me";
    private              UsersRepository       userRepository;
    private              BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public void setUsersRepository(UsersRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setBCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder)
    {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("/facebook-callback")
    public void signUp(@RequestParam("code") String code, @RequestParam("state") String state) throws
        InterruptedException, ExecutionException, IOException
    {
        final String clientId     = "";
        final String clientSecret = "";
        final OAuth20Service service = new ServiceBuilder(clientId)
            .apiSecret(clientSecret)
            .callback("https://localhost:8181/account/facebook-callback")
            .build(FacebookApi.instance());

        final OAuth2AccessToken accessToken = service.getAccessToken(code);

        System.out.println(accessToken);

        final OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);

        request.addParameter("fields", "id,name,email");

        service.signRequest(accessToken, request);

        try (Response response = service.execute(request))
        {
            System.out.println();
            System.out.println(response.getCode());
            System.out.println(response.getBody());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

//        User user = new User(null, username, bCryptPasswordEncoder.encode(password));

//        userRepository.save(user);
    }
}
