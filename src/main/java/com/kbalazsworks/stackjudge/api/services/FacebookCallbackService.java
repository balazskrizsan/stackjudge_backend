package com.kbalazsworks.stackjudge.api.services;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.kbalazsworks.stackjudge.api.builders.OAuthFacebookServiceBuilder;
import com.kbalazsworks.stackjudge.api.services.facebook_callback_service.GetJwtLoginUrlService;
import com.kbalazsworks.stackjudge.api.value_objects.FacebookUser;
import com.kbalazsworks.stackjudge.domain.common_module.services.JooqService;
import com.kbalazsworks.stackjudge.state.entities.User;
import com.kbalazsworks.stackjudge.state.services.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Configuration;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FacebookCallbackService
{
    private final AccountService              accountService;
    private final GetJwtLoginUrlService       getJwtLoginUrlService;
    private final RegistrationStateService    registrationStateService;
    private final OAuthFacebookServiceBuilder oAuthFacebookServiceBuilder;
    private final JooqService                 jooqService;

    private static final String FACEBOOK_GRAPH_API = "https://graph.facebook.com/v10.0/me";

    // @todo: test: callWithValidCodeWithExistingUser_returnsValidRedirectUrlAndUpdateTheFacebookAccessToken
    // @todo: test: callWithValidCodeWithNotExistingUser_returnsValidRedirectUrlAndCreateNewUser
    // @todo: test: callWithValidCodeGenerateLoginUrlThrowsException_logTheErrorAndRollbackTheDatabase
    public String getJwtLoginUrl(String code, String state)
    {
        if (!registrationStateService.exists(state))
        {
            log.error("Facebook authentication error with state: " + state);

            return getJwtLoginUrlService.generateLoginErrorUrl();
        }

        try
        {
            registrationStateService.delete(state);

            return jooqService.getDbContext().transactionResult((Configuration config) -> runTransaction(code));
        }
        catch (Exception e)
        {
            log.error("Facebook authentication error with state: " + e.getMessage(), e);

            return getJwtLoginUrlService.generateLoginErrorUrl();
        }
    }

    private String runTransaction(String code) throws Exception
    {
        OAuth20Service    service     = oAuthFacebookServiceBuilder.create();
        OAuth2AccessToken accessToken = getJwtLoginUrlService.getAccessToken(service, code);

        OAuthRequest request = new OAuthRequest(Verb.GET, FACEBOOK_GRAPH_API);
        request.addParameter("fields", "id,name,picture");
        service.signRequest(accessToken, request);

        Response     response     = getJwtLoginUrlService.getFacebookResponse(service, request);
        FacebookUser facebookUser = getJwtLoginUrlService.getFacebookUser(response);

        User user = accountService.findByFacebookId(facebookUser.getId());
        log.info("Login callback: FacebookId: {}", facebookUser.getId());
        if (null != user)
        {
            log.info("Login callback: login with UserId: {}", user.getId());
            accountService.updateFacebookAccessToken(accessToken.getAccessToken(), facebookUser.getId());

            return getJwtLoginUrlService.generateLoginUrl(user);
        }
        log.info("Login callback: create new user with Facebook");

        user = accountService.create(new User(
            null,
            false,
            true,
            facebookUser.getPictureUrl(),
            facebookUser.getName(),
            "random",
            accessToken.getAccessToken(),
            facebookUser.getId()
        ));

        log.info("User created {}", user.getId());

        return getJwtLoginUrlService.generateLoginUrl(user);
    }
}
