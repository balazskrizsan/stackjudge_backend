package com.kbalazsworks.stackjudge.api.services.facebook_services;

import com.github.scribejava.core.oauth.OAuth20Service;
import com.kbalazsworks.stackjudge.api.builders.OAuthFacebookServiceBuilder;
import com.kbalazsworks.stackjudge.api.services.JwtService;
import com.kbalazsworks.stackjudge.api.services.RegistrationStateService;
import com.kbalazsworks.stackjudge.api.services.SpringCookieService;
import com.kbalazsworks.stackjudge.common.services.SecureRandomService;
import com.kbalazsworks.stackjudge.domain.common_module.services.JooqService;
import com.kbalazsworks.stackjudge.state.entities.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jooq.Configuration;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import static com.kbalazsworks.stackjudge.api.config.SecurityConstants.AUTHENTICATION_COOKIE_NAME;
import static com.kbalazsworks.stackjudge.api.config.SecurityConstants.BEARER_TOKEN_PREFIX;

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
    private final JwtService                  jwtService;
    private final SpringCookieService         springCookieService;

    public String redirectToRegistrationAndLogin()
    {
        log.info("Login started");
        String currentState = secureRandomService.getUrlEncoded(32);

        registrationStateService.add(currentState, 24);

        OAuth20Service service = oAuthFacebookServiceBuilder.build();

        return service.createAuthorizationUrlBuilder().state(currentState).build();
    }

    public String registerAndLoginAndRedirect(
        HttpServletResponse response,
        String code,
        String state
    ) throws Exception
    {
        if (!registrationStateService.exists(state))
        {
            log.error("Authentication error with state: " + state);

            return registrationAndLoginService.generateLoginErrorUrl();
        }

        registrationStateService.delete(state);

        User user = jooqService
            .getDbContext()
            .transactionResult((Configuration config) -> registrationAndLoginService.updateOrSaveUser(code));

        Cookie authCookie = new Cookie(
            AUTHENTICATION_COOKIE_NAME,
            BEARER_TOKEN_PREFIX.concat(jwtService.generateAccessToken(user))
        );
        authCookie.setPath("/");
        authCookie.setSecure(true);
        authCookie.setHttpOnly(true);
        authCookie.setMaxAge(60 * 60 * 24 * 7);

        springCookieService.set(response, authCookie);

        log.info("User login attempted id#{}", user.getId());

        return registrationAndLoginService.generateLoginUrl();
    }
}
