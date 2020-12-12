package com.kbalazsworks.stackjudge.api.service;

import com.kbalazsworks.stackjudge.api.dto.LocalUser;
import com.kbalazsworks.stackjudge.api.model.User;
import com.kbalazsworks.stackjudge.api.util.GeneralUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("localUserDetailService")
public class LocalUserDetailService implements UserDetailsService
{
    @Autowired
    private UserService userService;

    @Override
//    @Transactional
    public LocalUser loadUserByUsername(final String email) throws UsernameNotFoundException
    {
        User user = userService.findUserByEmail(email);
        if (user == null)
        {
            throw new UsernameNotFoundException("User " + email + " was not found in the database");
        }
        return new LocalUser(
            user.getEmail(),
            user.getPassword(),
            user.isEnabled(),
            true,
            true,
            true,
            GeneralUtils.buildSimpleGrantedAuthorities(user.getRoles()),
            user
        );
    }
}
