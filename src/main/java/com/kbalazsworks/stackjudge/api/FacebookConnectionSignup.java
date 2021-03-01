package com.kbalazsworks.stackjudge.api;

import com.kbalazsworks.stackjudge.state.entities.User;
import com.kbalazsworks.stackjudge.state.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Service;

@Service
public class FacebookConnectionSignup implements ConnectionSignUp
{
    private UsersRepository userRepository;

    @Autowired
    public void setUsersRepository(UsersRepository applicationUserRepository)
    {
        this.userRepository = applicationUserRepository;
    }

    @Override
    public String execute(Connection<?> connection) {
        System.out.println("signup === ");
        User user = new User();
//        user.setUsername(connection.getDisplayName());
        user.setUsername("very_long_random_username");
        user.setPassword("very_long_random_asswortd");
        System.out.println(user);
        System.out.println(connection.getKey());
//        System.out.println(connection.fetchUserProfile());
        userRepository.save(user);
        return user.getUsername();
    }
}
