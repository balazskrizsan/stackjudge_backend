package com.kbalazsworks.stackjudge.state.services;

import com.kbalazsworks.stackjudge.state.entities.User;
import com.kbalazsworks.stackjudge.state.repositories.UsersRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService
{
    private final UsersRepository usersRepository;

    public @NonNull User create(User user)
    {
        return usersRepository.save(user);
    }

    public User findByFacebookId(long facebookId)
    {
        return usersRepository.findByFacebookId(facebookId);
    }

    public void updateFacebookAccessToken(String token, Long facebookUserId)
    {
        usersRepository.updateFacebookAccessToken(token, facebookUserId);
    }
}
