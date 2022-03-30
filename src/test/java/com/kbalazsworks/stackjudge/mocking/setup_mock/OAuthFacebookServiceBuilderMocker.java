package com.kbalazsworks.stackjudge.mocking.setup_mock;

import com.github.scribejava.core.oauth.AuthorizationUrlBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.kbalazsworks.stackjudge.api.builders.OAuthFacebookServiceBuilder;
import com.kbalazsworks.stackjudge.mocking.MockCreator;

import static org.mockito.Mockito.when;

public class OAuthFacebookServiceBuilderMocker
{
    public static OAuthFacebookServiceBuilder createAuthorizationUrlBuilder_state_build_return_string(
        String whenState,
        String thanRedirectUri
    )
    {
        AuthorizationUrlBuilder authorizationUrlBuilderMock = MockCreator.getAuthorizationUrlBuilderMock();
        when(authorizationUrlBuilderMock.state(whenState)).thenReturn(authorizationUrlBuilderMock);
        when(authorizationUrlBuilderMock.build()).thenReturn(thanRedirectUri);

        OAuth20Service oAuth20ServiceMock = MockCreator.getOAuth20ServiceMock();
        when(oAuth20ServiceMock.createAuthorizationUrlBuilder()).thenReturn(authorizationUrlBuilderMock);

        OAuthFacebookServiceBuilder oAuthFacebookServiceBuilderMock = MockCreator.getOAuthFacebookServiceBuilder();
        when(oAuthFacebookServiceBuilderMock.build()).thenReturn(oAuth20ServiceMock);

        return oAuthFacebookServiceBuilderMock;
    }
}
