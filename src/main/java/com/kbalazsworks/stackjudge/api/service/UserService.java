package com.kbalazsworks.stackjudge.api.service;

import com.kbalazsworks.stackjudge.api.dto.LocalUser;
import com.kbalazsworks.stackjudge.api.dto.UserRegistrationForm;
import com.kbalazsworks.stackjudge.api.exception.UserAlreadyExistAuthenticationException;
import com.kbalazsworks.stackjudge.api.model.User;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import java.util.Map;

public interface UserService
{
    public User registerNewUser(UserRegistrationForm UserRegistrationForm) throws
        UserAlreadyExistAuthenticationException;

    User findUserByEmail(String email);

    LocalUser processUserRegistration(
        String registrationId,
        Map<String, Object> attributes,
        OidcIdToken idToken,
        OidcUserInfo userInfo
    );
}
