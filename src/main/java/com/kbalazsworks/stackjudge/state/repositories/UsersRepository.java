package com.kbalazsworks.stackjudge.state.repositories;

import com.kbalazsworks.stackjudge.state.entities.User;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsersRepository extends JpaRepository<User, Long>
{
    User findByUsername(String username);

    @Override @NotNull
    List<User> findAllById(@NotNull Iterable<Long> ids);

    User findByFacebookId(@Param("facebook_id") Long facebookId);

    <S extends User> @NonNull S save(@NonNull S entity);

    @Modifying
    @Query(
        value = "UPDATE users SET facebook_access_token = :facebookAccessToken where facebook_id = :facebookId",
        nativeQuery = true
    )
    void updateFacebookAccessToken(
        @Param("facebookAccessToken") String facebookAccessToken,
        @Param("facebookId") Long facebookId
    );
}
