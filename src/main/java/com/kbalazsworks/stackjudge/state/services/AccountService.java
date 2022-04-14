package com.kbalazsworks.stackjudge.state.services;

import com.kbalazsworks.stackjudge.domain.persistance_log_module.entities.ProtectedReviewLog;
import com.kbalazsworks.stackjudge.domain.review_module.services.ProtectedReviewLogService;
import com.kbalazsworks.stackjudge.state.entities.PushoverInfo;
import com.kbalazsworks.stackjudge.state.entities.State;
import com.kbalazsworks.stackjudge.state.entities.User;
import com.kbalazsworks.stackjudge.state.exceptions.StateException;
import com.kbalazsworks.stackjudge.state.repositories.UserJooqRepository;
import com.kbalazsworks.stackjudge.state.repositories.UsersRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private final UserJooqRepository        userJooqRepository;
    private final ProtectedReviewLogService protectedReviewLogService;

    public @NonNull User create(@NonNull User user) throws Exception
    {
        return userJooqRepository.create(user);
    }

    public @NonNull List<User> findByIds(@NonNull List<Long> ids)
    {
        return usersRepository.findAllById(ids);
    }

    public @NonNull PushoverInfo findPushoverDataById(@NonNull Long id) throws StateException
    {
        return new PushoverInfo(id, userJooqRepository.findPushoverUserTokenById(id));
    }

    public @NonNull Map<Long, User> findByIdsWithIdMap(List<Long> ids)
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

        throw new UsernameNotFoundException("User not found with id#" + id);
    }

    public User findByFacebookId(long facebookId)
    {
        return usersRepository.findByFacebookId(facebookId);
    }

    // @todo: test after JPA commit problem solved in this.create
    public void updateFacebookAccessToken(String token, Long facebookUserId)
    {
        userJooqRepository.updateFacebookAccessToken(token, facebookUserId);
    }

    // @todo: test
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
