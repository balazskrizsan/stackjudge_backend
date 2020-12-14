package com.kbalazsworks.stackjudge.api.controllers.account_controller;

import com.github.scribejava.apis.LinkedInApi;
import com.github.scribejava.apis.LinkedInApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth10aService;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.kbalazsworks.stackjudge.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.stackjudge.api.value_objects.ResponseData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.Scanner;

@RestController("AccountPostAction")
@RequestMapping(AccountConfig.CONTROLLER_URI)
public class PostAction
{
    @PostMapping("/registration")
    public ResponseEntity<ResponseData<String>> action() throws Exception
    {
        final String clientId = "77lze1mnh19gxf";
        final String clientSecret = "2GY0lnx040Kt47wR";
        final OAuth20Service service = new ServiceBuilder(clientId)
            .apiSecret(clientSecret)
//            .defaultScope("r_liteprofile r_emailaddress") // replace with desired scope
            .defaultScope("") // replace with desired scope
            .callback("http://localhost:8181/oauth-callback")
            .build(LinkedInApi20.instance());

        ResponseEntityBuilder<String> responseEntityBuilder = new ResponseEntityBuilder<>();

        final Scanner in = new Scanner(System.in);

        System.out.println("=== SALALA's OAuth Workflow ===");
        System.out.println();

        // Obtain the Authorization URL
        System.out.println("Fetching the Authorization URL...");
        final String secretState = "secret" + new Random().nextInt(999_999);
        final String authorizationUrl = service.getAuthorizationUrl(secretState);

        System.out.println("Got the Authorization URL!");
        System.out.println("Now go and authorize ScribeJava here:");
        System.out.println(authorizationUrl);
        System.out.println("And paste the authorization code here");
        System.out.print(">>");

        final String code = in.nextLine();

        responseEntityBuilder.setData("");

        return responseEntityBuilder.build();
    }
}
