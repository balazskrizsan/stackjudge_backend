package com.kbalazsworks.stackjudge.state.services;

import com.kbalazsworks.stackjudge.state.entities.User;
import com.kbalazsworks.stackjudge.state.repositories.UsersRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService
{
    private final UsersRepository usersRepository;

    public @NonNull User create(User user)
    {
        return usersRepository.save(user);
    }

    public @NotNull List<User> findByUserIds(List<Long> ids)
    {
        return usersRepository.findAllById(ids);
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
