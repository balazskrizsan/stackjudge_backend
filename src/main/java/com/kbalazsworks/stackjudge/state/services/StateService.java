package com.kbalazsworks.stackjudge.state.services;

import com.kbalazsworks.stackjudge.state.entities.State;
import com.kbalazsworks.stackjudge.state.entities.User;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;

@Service
public class StateService
{
    public State getState()
    {
        User user = new User(1L, "temp user", "password");

        return new State(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(), user);
    }
}
