package com.kbalazsworks.stackjudge.domain.entities;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("RegistrationSecret")
@AllArgsConstructor
public class RegistrationSecret implements Serializable
{
    private String id;
    private String state;
}
