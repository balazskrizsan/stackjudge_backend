package com.kbalazsworks.stackjudge.api.service;

import com.kbalazsworks.stackjudge.api.dto.LocalUser;
import com.kbalazsworks.stackjudge.api.dto.SocialProvider;
import com.kbalazsworks.stackjudge.api.dto.UserRegistrationForm;
import com.kbalazsworks.stackjudge.api.exception.OAuth2AuthenticationProcessingException;
import com.kbalazsworks.stackjudge.api.exception.UserAlreadyExistAuthenticationException;
import com.kbalazsworks.stackjudge.api.model.Role;
import com.kbalazsworks.stackjudge.api.model.User;
import com.kbalazsworks.stackjudge.api.oauth2.user.OAuth2UserInfo;
import com.kbalazsworks.stackjudge.api.oauth2.user.OAuth2UserInfoFactory;
import com.kbalazsworks.stackjudge.api.repo.RoleRepository;
import com.kbalazsworks.stackjudge.api.repo.UserRepository;
import com.kbalazsworks.stackjudge.api.util.GeneralUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService
{

    @Autowired
    @Qualifier(value = "localUserDetailService")
    private UserDetailsService userDetailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
//    @Transactional(value = "transactionManager")
    public User registerNewUser(final UserRegistrationForm userRegistrationForm) throws
        UserAlreadyExistAuthenticationException
    {
        if (userRegistrationForm.getUserID() != null && userRepository.existsById(userRegistrationForm.getUserID()))
        {
            throw new UserAlreadyExistAuthenticationException("User with User id "
                                                                  + userRegistrationForm.getUserID()
                                                                  + " already exist");
        }
        else if (userRepository.existsByEmail(userRegistrationForm.getEmail()))
        {
            throw new UserAlreadyExistAuthenticationException("User with email id "
                                                                  + userRegistrationForm.getEmail()
                                                                  + " already exist");
        }
        User user = buildUser(userRegistrationForm);
        Date now  = Calendar.getInstance().getTime();
        user.setCreatedDate(now);
        user.setModifiedDate(now);
        user = userRepository.save(user);
        userRepository.flush();
        return user;
    }

    private User buildUser(final UserRegistrationForm formDTO)
    {
        User user = new User();
        user.setDisplayName(formDTO.getDisplayName());
        user.setEmail(formDTO.getEmail());
        user.setPassword(passwordEncoder.encode(formDTO.getPassword()));
        final HashSet<Role> roles = new HashSet<Role>();
        roles.add(roleRepository.findByName(Role.ROLE_USER));
        user.setRoles(roles);
        user.setProvider(formDTO.getSocialProvider().getProviderType());
        user.setEnabled(true);
        user.setProviderUserId(formDTO.getProviderUserId());
        return user;
    }

    @Override
    public User findUserByEmail(final String email)
    {
        return userRepository.findByEmail(email);
    }

    @Override
//    @Transactional
    public LocalUser processUserRegistration(
        String registrationId,
        Map<String, Object> attributes,
        OidcIdToken idToken,
        OidcUserInfo userInfo
    )
    {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, attributes);
        if (StringUtils.isEmpty(oAuth2UserInfo.getName()))
        {
            throw new OAuth2AuthenticationProcessingException("Name not found from OAuth2 provider");
        }
        else if (StringUtils.isEmpty(oAuth2UserInfo.getEmail()))
        {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }
        UserRegistrationForm userDetails = toUserRegistrationObject(registrationId, oAuth2UserInfo);
        User                 user        = findUserByEmail(oAuth2UserInfo.getEmail());
        if (user != null)
        {
            if (!user.getProvider().equals(registrationId) && !user.getProvider()
                .equals(SocialProvider.LOCAL.getProviderType()))
            {
                throw new OAuth2AuthenticationProcessingException(
                    "Looks like you're signed up with "
                        + user.getProvider()
                        + " account. Please use your "
                        + user.getProvider()
                        + " account to login.");
            }
            user = updateExistingUser(user, oAuth2UserInfo);
        }
        else
        {
            user = registerNewUser(userDetails);
        }

        return LocalUser.create(user, attributes, idToken, userInfo);
    }

    private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo)
    {
        existingUser.setDisplayName(oAuth2UserInfo.getName());
        return userRepository.save(existingUser);
    }

    private UserRegistrationForm toUserRegistrationObject(String registrationId, OAuth2UserInfo oAuth2UserInfo)
    {
        return UserRegistrationForm.getBuilder()
            .addProviderUserID(oAuth2UserInfo.getId())
            .addDisplayName(oAuth2UserInfo.getName())
            .addEmail(oAuth2UserInfo.getEmail())
            .addSocialProvider(GeneralUtils.toSocialProvider(registrationId))
            .addPassword("changeit")
            .build();
    }
}
