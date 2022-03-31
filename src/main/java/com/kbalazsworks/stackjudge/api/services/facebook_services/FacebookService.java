package com.kbalazsworks.stackjudge.api.services.facebook_services;

import com.github.scribejava.core.oauth.OAuth20Service;
import com.kbalazsworks.stackjudge.api.builders.OAuthFacebookServiceBuilder;
import com.kbalazsworks.stackjudge.api.services.RegistrationStateService;
import com.kbalazsworks.stackjudge.common.services.SecureRandomService;
import com.kbalazsworks.stackjudge.domain.common_module.services.JooqService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jooq.Configuration;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class FacebookService
{
    private final SecureRandomService         secureRandomService;
    private final RegistrationStateService    registrationStateService;
    private final OAuthFacebookServiceBuilder oAuthFacebookServiceBuilder;
    private final RegistrationAndLoginService registrationAndLoginService;
    private final JooqService                 jooqService;

    public String redirectToRegistrationAndLogin()
    {
        log.info("Login started");
        String currentState = secureRandomService.getUrlEncoded(32);

        registrationStateService.add(currentState, 24);

        OAuth20Service service = oAuthFacebookServiceBuilder.build();

        return service.createAuthorizationUrlBuilder().state(currentState).build();
    }

    // @todo: test: existingState_returnsLoginAndRemovesStateFromRedis
    public String registerAndLoginAndRedirect(String code, String state)
    {
        if (!registrationStateService.exists(state))
        {
            log.error("Facebook authentication error with state: " + state);

            return registrationAndLoginService.generateLoginErrorUrl();
        }

        try
        {
            registrationStateService.delete(state);

            return jooqService
                .getDbContext()
                .transactionResult((Configuration config) -> registrationAndLoginService.updateOrSaveUser(code));
        }
        catch (Exception e)
        {
            log.error("Facebook authentication error with state: " + e.getMessage(), e);

            return registrationAndLoginService.generateLoginErrorUrl();
        }
    }
}
