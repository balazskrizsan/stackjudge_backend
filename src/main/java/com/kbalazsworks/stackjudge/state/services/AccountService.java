package com.kbalazsworks.stackjudge.state.services;

import com.amazonaws.services.connect.model.UserNotFoundException;
import com.kbalazsworks.stackjudge.domain.entities.ProtectedReviewLog;
import com.kbalazsworks.stackjudge.domain.services.ProtectedReviewLogService;
import com.kbalazsworks.stackjudge.state.entities.State;
import com.kbalazsworks.stackjudge.state.entities.User;
import com.kbalazsworks.stackjudge.state.repositories.UsersRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService
{
    private final UsersRepository           usersRepository;
    private final ProtectedReviewLogService protectedReviewLogService;

    public @NonNull User create(User user)
    {
        return usersRepository.save(user);
    }

    public @NonNull List<User> findByIds(List<Long> ids)
    {
        return usersRepository.findAllById(ids);
    }

    public @NonNull Map<Long, User> findByUserIdsWithIdMap(List<Long> ids)
    {
        return findByIds(ids)
            .stream()
            .distinct()
            .collect(Collectors.toMap(User::getId, Function.identity()));
    }

    public @NonNull User findById(Long id)
    {
        Optional<User> user = usersRepository.findById(id);
        if (user.isPresent())
        {
            return user.get();
        }

        throw new UserNotFoundException("User not found with id#" + id);
    }

    public User findByFacebookId(long facebookId)
    {
        return usersRepository.findByFacebookId(facebookId);
    }

    public void updateFacebookAccessToken(String token, Long facebookUserId)
    {
        usersRepository.updateFacebookAccessToken(token, facebookUserId);
    }

    public User getCurrentUser()
    {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public User getByReviewId(long reviewId, State state)
    {
        User user = usersRepository.getByReviewId(reviewId);

        protectedReviewLogService.create(new ProtectedReviewLog(null, user.getId(), reviewId, state.now()), state);

        return user;
    }
}
