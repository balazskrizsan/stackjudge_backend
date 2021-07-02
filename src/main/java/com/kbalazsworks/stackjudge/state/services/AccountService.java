package com.kbalazsworks.stackjudge.state.services;

import com.amazonaws.services.connect.model.UserNotFoundException;
import com.kbalazsworks.stackjudge.domain.entities.ProtectedReviewLog;
import com.kbalazsworks.stackjudge.domain.services.ProtectedReviewLogService;
import com.kbalazsworks.stackjudge.state.entities.State;
import com.kbalazsworks.stackjudge.state.entities.User;
import com.kbalazsworks.stackjudge.state.repositories.UsersRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public @NotNull List<User> findByUserIds(List<Long> ids)
    {
        return usersRepository.findAllById(ids);
    }

    public @NotNull User findByUserId(Long id)
    {
        Optional<User> user = usersRepository.findById(id);
        if (user.isPresent())
        {
            return user.get();
        }

        // @todo: add HTTP exception
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

    // @todo: test
    public User getByReviewId(long reviewId, State state)
    {
        protectedReviewLogService.create(
            new ProtectedReviewLog(
                null,
                state.currentUser().getId(),
                reviewId,
                state.now()
            )
        );

        return usersRepository.getByReviewId(reviewId);
    }
}
