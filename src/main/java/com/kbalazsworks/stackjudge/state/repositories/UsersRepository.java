package com.kbalazsworks.stackjudge.state.repositories;

import com.kbalazsworks.stackjudge.state.entities.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Long>
{
    User findByUsername(String username);

    @Override @NonNull
    List<User> findAllById(@NonNull Iterable<Long> ids);

    @Override @NonNull
    Optional<User> findById(@NonNull Long id);

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

    @Query(
        value = "SELECT * FROM users" +
            "    LEFT JOIN review" +
            "          ON review.created_by = users.id" +
            "    WHERE review.id = :reviewId",
        nativeQuery = true
    )
    User getByReviewId(@Param("reviewId") long reviewId);
}
