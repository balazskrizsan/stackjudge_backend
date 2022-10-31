package com.kbalazsworks.stackjudge.state.repositories;

import com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.ids._entities.IdsUser;
import com.kbalazsworks.stackjudge.state.entities.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, String>
{
//    IdsUser findByUsername(String username);

    @Override
    @NonNull
    List<User> findAllById(@NonNull Iterable<String> ids);

    @Override @NonNull
    Optional<User> findById(@NonNull String id);

    @Query(
        value = "SELECT * FROM users" +
            "    LEFT JOIN review" +
            "          ON review.created_by = users.id" +
            "    WHERE review.id = :reviewId",
        nativeQuery = true
    )
    IdsUser getByReviewId(@Param("reviewId") long reviewId);
}
