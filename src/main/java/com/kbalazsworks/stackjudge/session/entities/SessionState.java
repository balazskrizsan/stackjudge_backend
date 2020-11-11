package com.kbalazsworks.stackjudge.session.entities;

import java.time.LocalDateTime;

public class SessionState
{
    private final LocalDateTime now;
    private final User          user;

    public SessionState(LocalDateTime now, User user)
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
