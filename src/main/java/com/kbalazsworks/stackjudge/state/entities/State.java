package com.kbalazsworks.stackjudge.state.entities;

import com.kbalazsworks.stackjudge.stackjudge_microservice_sdks.ids._entities.IdsUser;

import java.time.LocalDateTime;

public record State(LocalDateTime now, IdsUser currentIdsUser)
{
}
