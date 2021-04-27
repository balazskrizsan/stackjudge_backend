package com.kbalazsworks.stackjudge.state.services;

import com.kbalazsworks.stackjudge.state.entities.User;
import com.kbalazsworks.stackjudge.state.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
    private UsersRepository usersRepository;

    @Autowired
    public void setUsersRepository(UsersRepository usersRepository)
    {
        this.usersRepository = usersRepository;
    }

    public void create(User user) {
        usersRepository.save(user);
    }
}
