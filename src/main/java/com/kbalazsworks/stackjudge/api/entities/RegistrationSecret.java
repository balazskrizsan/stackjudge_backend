package com.kbalazsworks.stackjudge.api.entities;

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
