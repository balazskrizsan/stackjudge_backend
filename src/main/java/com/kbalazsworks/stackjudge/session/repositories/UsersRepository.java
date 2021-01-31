package com.kbalazsworks.stackjudge.session.repositories;

import com.kbalazsworks.stackjudge.session.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, Long>
{
    User findByUsername(String username);
}
