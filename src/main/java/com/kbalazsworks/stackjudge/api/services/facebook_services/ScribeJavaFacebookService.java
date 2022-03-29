package com.kbalazsworks.stackjudge.api.services.facebook_services;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.kbalazsworks.stackjudge.api.builders.OAuthFacebookServiceBuilder;
import com.kbalazsworks.stackjudge.api.services.facebook_services.scribe_java_facebook_service.GetFacebookUserWithTokenFromCodeService;
import com.kbalazsworks.stackjudge.api.value_objects.FacebookUser;
import com.kbalazsworks.stackjudge.api.value_objects.FacebookUserWithAccessToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScribeJavaFacebookService
{
    private final GetFacebookUserWithTokenFromCodeService getFacebookUserWithTokenFromCodeService;
    private final OAuthFacebookServiceBuilder             oAuthFacebookServiceBuilder;

    private static final String FACEBOOK_GRAPH_API = "https://graph.facebook.com/v10.0/me";

    public FacebookUserWithAccessToken getFacebookUserWithAccessTokenFromCode(String code)
    {
        OAuth20Service    service     = oAuthFacebookServiceBuilder.build();
        OAuth2AccessToken accessToken = getFacebookUserWithTokenFromCodeService.getAccessToken(service, code);

        OAuthRequest request = new OAuthRequest(Verb.GET, FACEBOOK_GRAPH_API);
        request.addParameter("fields", "id,name,picture");
        service.signRequest(accessToken, request);

        Response     response     = getFacebookUserWithTokenFromCodeService.getFacebookResponse(service, request);
        FacebookUser facebookUser = getFacebookUserWithTokenFromCodeService.getFacebookUser(response);

        return new FacebookUserWithAccessToken(facebookUser, accessToken);
    }
}
