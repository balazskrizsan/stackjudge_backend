package com.kbalazsworks.stackjudge.state.services;

import com.kbalazsworks.stackjudge.state.entities.User;
import lombok.SneakyThrows;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    private final AccountService accountService;

    public UserDetailsServiceImpl(AccountService accountService)
    {
        this.accountService = accountService;
    }

    @SneakyThrows @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        var idsUser =  (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (idsUser == null)
        {
            throw new UsernameNotFoundException(username);
        }

        return new org.springframework.security.core.userdetails.User(
            idsUser.getIdsUserId(),
            new BCryptPasswordEncoder().encode("random"),
            emptyList()
        );
    }
}
