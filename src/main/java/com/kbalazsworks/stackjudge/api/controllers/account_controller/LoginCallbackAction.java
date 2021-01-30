package com.kbalazsworks.stackjudge.api.controllers.account_controller;

import com.github.scribejava.apis.FacebookApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Random;

@RestController("AccountAction")
@RequestMapping(AccountConfig.CONTROLLER_URI)
public class LoginCallbackAction
{
    private static final String NETWORK_NAME           = "Facebook";
    private static final String PROTECTED_RESOURCE_URL = "https://graph.facebook.com/v3.2/me";

    @GetMapping("/registration/callback")
    public RedirectView action() throws Exception
    {
        final String clientId     = "149500276863432";
        final String clientSecret = "4738a5809b56a5a2087fb923958fdd4d";
        final String secretState  = "secret" + new Random().nextInt(999_999);
        final OAuth20Service service = new ServiceBuilder(clientId)
            .apiSecret(clientSecret)
            .callback("https://localhost:8181/account/registration/callback")
            .build(FacebookApi.instance());

        return new RedirectView(service.getAuthorizationUrl(secretState));

//        System.out.println("And paste the authorization code here");
//        System.out.print(">>");
//        final String code = in.nextLine();
//        System.out.println();
//
//        System.out.println("And paste the state from server here. We have set 'secretState'='" + secretState + "'.");
//        System.out.print(">>");
//        final String value = in.nextLine();
//        if (secretState.equals(value))
//        {
//            System.out.println("State value does match!");
//        }
//        else
//        {
//            System.out.println("Ooops, state value does not match!");
//            System.out.println("Expected = " + secretState);
//            System.out.println("Got      = " + value);
//            System.out.println();
//        }
//
//        System.out.println("Trading the Authorization Code for an Access Token...");
//        final OAuth2AccessToken accessToken = service.getAccessToken(code);
//        System.out.println("Got the Access Token!");
//        System.out.println("(The raw response looks like this: " + accessToken.getRawResponse() + "')");
//        System.out.println();
//
//        // Now let's go and ask for a protected resource!
//        System.out.println("Now we're going to access a protected resource...");
//        final OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
//        service.signRequest(accessToken, request);
//        try (Response response = service.execute(request))
//        {
//            System.out.println("Got it! Lets see what we found...");
//            System.out.println();
//            System.out.println(response.getCode());
//            System.out.println(response.getBody());
//        }
//        System.out.println();
//        System.out.println("Thats it man! Go and build something awesome with ScribeJava! :)");
//
//        ResponseEntityBuilder<String> responseEntityBuilder = new ResponseEntityBuilder<>();
//        responseEntityBuilder.setData("");
//
//        return responseEntityBuilder.build();
    }
}