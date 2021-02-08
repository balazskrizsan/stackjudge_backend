package com.kbalazsworks.stackjudge.api.controllers.account_controller;

import com.github.scribejava.apis.FacebookApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.kbalazsworks.stackjudge.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.stackjudge.api.value_objects.ResponseData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController("AccountRegistrationAction")
@RequestMapping(AccountConfig.CONTROLLER_URI)
public class RegistrationAction
{
    @GetMapping("/registration")
    public ResponseEntity<ResponseData<String>> action() throws Exception
    {
        final String clientId     = "";
        final String clientSecret = "";
        final OAuth20Service service = new ServiceBuilder(clientId)
            .apiSecret(clientSecret)
            .callback("https://localhost:8181/account/facebook-callback")
            .build(FacebookApi.instance());


        // Obtain the Authorization URL
        final String secretState      = "secret" + new Random().nextInt(999_999);
        final String authorizationUrl = service.getAuthorizationUrl(secretState);

        // redir to
        return new ResponseEntityBuilder<String>().setData(authorizationUrl).build();
    }
}
