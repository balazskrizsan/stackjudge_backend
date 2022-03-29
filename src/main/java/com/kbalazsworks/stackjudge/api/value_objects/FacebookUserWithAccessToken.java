package com.kbalazsworks.stackjudge.api.value_objects;

import com.github.scribejava.core.model.OAuth2AccessToken;

public record FacebookUserWithAccessToken(FacebookUser facebookUser, OAuth2AccessToken accessToken)
{
}
