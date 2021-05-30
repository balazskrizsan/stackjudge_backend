package com.kbalazsworks.stackjudge.api.services;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.kbalazsworks.stackjudge.api.builders.OAuthFacebookServiceBuilder;
import com.kbalazsworks.stackjudge.api.exceptions.AuthException;
import com.kbalazsworks.stackjudge.api.services.facebook_callback_service.GetJwtLoginUrlService;
import com.kbalazsworks.stackjudge.api.value_objects.FacebookUser;
import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import com.kbalazsworks.stackjudge.state.entities.User;
import com.kbalazsworks.stackjudge.state.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class FacebookCallbackService
{
    private final UserService                 userService;
    private final GetJwtLoginUrlService       getJwtLoginUrlService;
    private final JwtService                  jwtService;
    private final FrontendUriService          frontendUriService;
    private final ApplicationProperties       applicationProperties;
    private final OAuthFacebookServiceBuilder oAuthFacebookServiceBuilder;

    private static final String FACEBOOK_GRAPH_API = "https://graph.facebook.com/v3.2/me";

    @Transactional
    public String getJwtLoginUrl(String code) throws AuthException
    {
        OAuth20Service    service     = oAuthFacebookServiceBuilder.create();
        OAuth2AccessToken accessToken = getJwtLoginUrlService.getAccessToken(service, code);

        OAuthRequest request = new OAuthRequest(Verb.GET, FACEBOOK_GRAPH_API);
        request.addParameter("fields", "id,name");
        service.signRequest(accessToken, request);

        Response     response     = getJwtLoginUrlService.getFacebookResponse(service, request);
        FacebookUser facebookUser = getJwtLoginUrlService.getFacebookUser(response);

        User user = userService.findByFacebookId(facebookUser.id());
        if (null != user)
        {
            userService.updateFacebookAccessToken(accessToken.getAccessToken(), facebookUser.id());

            return applicationProperties.getSiteFrontendHost().concat(
                frontendUriService.getAccountLoginJwt(jwtService.generateAccessToken(user))
            );
        }

        user = userService.create(new User(null,
            facebookUser.name(),
            "random",
            accessToken.getAccessToken(),
            facebookUser.id()
        ));

        return applicationProperties.getSiteFrontendHost().concat(
            frontendUriService.getAccountLoginJwt(jwtService.generateAccessToken(user))
        );
    }
}
