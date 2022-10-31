package com.kbalazsworks.stackjudge.state.services;

import com.kbalazsworks.stackjudge.state.entities.State;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class StateService
{
    private final AccountService accountService;

    public State getState()
    {
        return new State(
            new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),
            accountService.getCurrentUser()
        );
    }
}
