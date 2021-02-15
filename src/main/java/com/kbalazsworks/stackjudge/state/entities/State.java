package com.kbalazsworks.stackjudge.state.entities;

import java.time.LocalDateTime;

public class State
{
    private final LocalDateTime now;
    private final User          user;

    public State(LocalDateTime now, User user)
    {
        this.now  = now;
        this.user = user;
    }

    public LocalDateTime getNow()
    {
        return now;
    }

    public User getUser()
    {
        return user;
    }
}
