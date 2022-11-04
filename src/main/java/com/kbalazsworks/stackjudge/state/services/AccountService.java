package com.kbalazsworks.stackjudge.state.services;

import com.kbalazsworks.stackjudge.domain.persistance_log_module.entities.ProtectedReviewLog;
import com.kbalazsworks.stackjudge.domain.review_module.services.ProtectedReviewLogService;
import com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.ids._entities.IdsUser;
import com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.ids.account.ListService;
import com.kbalazsworks.stackjudge.state.entities.PushoverInfo;
import com.kbalazsworks.stackjudge.state.entities.State;
import com.kbalazsworks.stackjudge.state.entities.User;
import com.kbalazsworks.stackjudge.state.exceptions.StateException;
import com.kbalazsworks.stackjudge.state.repositories.UserJooqRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService
{
    private final UserJooqRepository userJooqRepository;
    private final ProtectedReviewLogService protectedReviewLogService;
    private final ListService listService;

    public @NonNull User createUser(@NonNull User idsUser) throws StateException
    {
        return userJooqRepository.create(idsUser);
    }

    public @NonNull User get(String idsUserId) throws StateException
    {
        return userJooqRepository.get(idsUserId);
    }

    public @NonNull PushoverInfo findPushoverDataById(@NonNull String idsUserId) throws Exception
    {
        return new PushoverInfo(idsUserId, userJooqRepository.findPushoverUserToken(idsUserId));
    }

    @SneakyThrows
    public @NonNull Map<String, IdsUser> findByIdsWithIdMap(List<String> ids)
    {
        return listService
            .execute().data().getExtendedUsers()
            .stream()
            .distinct()
            .collect(Collectors.toUnmodifiableMap(IdsUser::getId, Function.identity()));
    }

    @SneakyThrows
    public IdsUser findById(String id)
    {
        return listService.execute().data().getExtendedUsers().get(0);
    }

    public boolean isLoggedIn()
    {
        return null != SecurityContextHolder.getContext().getAuthentication();
    }

    // @todo: test
    public User getCurrentUser()
    {
        org.springframework.security.core.userdetails.User principalUser =
            (org.springframework.security.core.userdetails.User)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return new User(principalUser.getUsername());
    }
    public IdsUser getCurrentIdsUser()
    {
        if (!isLoggedIn())
        {
            return null;
        }

        return findById(getCurrentUser().getIdsUserId());
    }

    @SneakyThrows
    public IdsUser getByReviewId(long reviewId, State state)
    {
        protectedReviewLogService.create(new ProtectedReviewLog(
            null,
            state.currentIdsUser().getId(),
            reviewId,
            state.now()
        ), state);

        return listService.execute().data().getExtendedUsers().get(0);
    }
}
