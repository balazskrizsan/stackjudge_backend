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
import com.kbalazsworks.stackjudge.state.entities.User;
import com.kbalazsworks.stackjudge.state.services.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
@Slf4j
@RequiredArgsConstructor
public class FacebookCallbackService {
    private final JedisPool jedisPool;
    private final AccountService accountService;
    private final GetJwtLoginUrlService getJwtLoginUrlService;
    private final OAuthFacebookServiceBuilder oAuthFacebookServiceBuilder;

    private static final String FACEBOOK_GRAPH_API = "https://graph.facebook.com/v10.0/me";

    // @todo: test: callWithValidCodeWithExistingUser_returnsValidRedirectUrlAndUpdateTheFacebookAccessToken
    // @todo: test: callWithValidCodeWithNotExistingUser_returnsValidRedirectUrlAndCreateNewUser
    // @todo: test: callWithValidCodeGenerateLoginUrlThrowsException_logTheErrorAndRollbackTheDatabase
    @Transactional
    public String getJwtLoginUrl(String code, String state) throws AuthException {

        checkState(state);

        OAuth20Service service = oAuthFacebookServiceBuilder.create();

        OAuth2AccessToken accessToken = getJwtLoginUrlService.getAccessToken(service, code);

        OAuthRequest request = new OAuthRequest(Verb.GET, FACEBOOK_GRAPH_API);
        request.addParameter("fields", "id,name,picture");
        service.signRequest(accessToken, request);

        Response response = getJwtLoginUrlService.getFacebookResponse(service, request);
        FacebookUser facebookUser = getJwtLoginUrlService.getFacebookUser(response);

        User user = accountService.findByFacebookId(facebookUser.getId());
        if (null != user) {
            accountService.updateFacebookAccessToken(accessToken.getAccessToken(), facebookUser.getId());

            return getJwtLoginUrlService.generateLoginUrl(user);
        }

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

        return getJwtLoginUrlService.generateLoginUrl(user);
    }

    private void checkState(String state) {

        String savedState;
        try (Jedis jedis = jedisPool.getResource()) {
            savedState = jedis.get(state);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("The state could not be recovered!");
        }

        if (savedState == null) {
            log.error("Facebook authentication error with state: " + state);
            throw new AuthException();
        }
    }

}
