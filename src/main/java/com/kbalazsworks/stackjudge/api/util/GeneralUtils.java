package com.kbalazsworks.stackjudge.api.util;

import com.kbalazsworks.stackjudge.api.dto.SocialProvider;
import com.kbalazsworks.stackjudge.api.model.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GeneralUtils
{

    public static List<SimpleGrantedAuthority> buildSimpleGrantedAuthorities(final Set<Role> roles)
    {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles)
        {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    public static SocialProvider toSocialProvider(String providerId)
    {
        for (SocialProvider socialProvider : SocialProvider.values())
        {
            if (socialProvider.getProviderType().equals(providerId))
            {
                return socialProvider;
            }
        }
        return SocialProvider.LOCAL;
    }
}
