package com.kbalazsworks.stackjudge.api.services.facebook_services;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.kbalazsworks.stackjudge.api.services.FrontendUriService;
import com.kbalazsworks.stackjudge.api.value_objects.FacebookUser;
import com.kbalazsworks.stackjudge.api.value_objects.FacebookUserWithAccessToken;
import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import com.kbalazsworks.stackjudge.state.entities.User;
import com.kbalazsworks.stackjudge.state.services.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegistrationAndLoginService
{
    private final AccountService            accountService;
    private final ScribeJavaFacebookService scribeJavaFacebookService;
    private final FrontendUriService        frontendUriService;
    private final ApplicationProperties     applicationProperties;

    // @todo: test
    public String generateLoginUrl()
    {
        return applicationProperties.getSiteFrontendHost()
            + "/"
            + frontendUriService.getAccountLoginUrl();
    }

    // @todo: test
    public String generateLoginErrorUrl()
    {
        return applicationProperties.getSiteFrontendHost() + "/" + frontendUriService.getAccountLoginErrorUrl();
    }

    // @todo: test
    public User updateOrSaveUser(String code) throws Exception
    {
        FacebookUserWithAccessToken facebookUserWithAccessToken = scribeJavaFacebookService
            .getFacebookUserWithAccessTokenFromCode(code);
        FacebookUser      facebookUser = facebookUserWithAccessToken.facebookUser();
        OAuth2AccessToken accessToken  = facebookUserWithAccessToken.accessToken();

        User user = accountService.findByFacebookId(facebookUser.getId());
        log.info("Login callback: FacebookId: {}", facebookUser.getId());
        if (null != user)
        {
            log.info("Login callback: login with UserId: {}", user.getId());
            accountService.updateFacebookAccessToken(accessToken.getAccessToken(), facebookUser.getId());

            return user;
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
        log.info("User created {}", user);

        return user;
    }
}
