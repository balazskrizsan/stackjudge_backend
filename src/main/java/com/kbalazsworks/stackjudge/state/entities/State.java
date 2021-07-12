package com.kbalazsworks.stackjudge.state.entities;

import java.time.LocalDateTime;

public record State(LocalDateTime now, User currentUser)
{
}
