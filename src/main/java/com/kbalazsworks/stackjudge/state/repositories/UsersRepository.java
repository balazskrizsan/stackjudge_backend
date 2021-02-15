package com.kbalazsworks.stackjudge.state.repositories;

import com.kbalazsworks.stackjudge.state.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, Long>
{
    User findByUsername(String username);
}
