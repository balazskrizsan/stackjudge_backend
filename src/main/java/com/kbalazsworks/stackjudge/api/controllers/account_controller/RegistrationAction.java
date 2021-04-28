package com.kbalazsworks.stackjudge.api.controllers.account_controller;

import com.github.scribejava.apis.FacebookApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.kbalazsworks.stackjudge.api.builders.ResponseEntityBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Random;

@RestController("AccountRegistrationAction")
@RequestMapping(AccountConfig.CONTROLLER_URI)
public class RegistrationAction
{
    @GetMapping("/registration")
    public RedirectView action() throws Exception
    {
        String clientId     = "149500276863432"; //@todo: remove and change
        String clientSecret = "4738a5809b56a5a2087fb923958fdd4d"; //@todo: remove and change

        OAuth20Service service = new ServiceBuilder(clientId)
            .apiSecret(clientSecret)
            .callback("https://localhost:8181/account/facebook-callback")
            .build(FacebookApi.instance());

        return new RedirectView(service.getAuthorizationUrl("secret" + new Random().nextInt(999_999)));
    }
}
