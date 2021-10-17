package com.kbalazsworks.stackjudge.domain.services;

import com.kbalazsworks.stackjudge.domain.entities.Address;
import lombok.NonNull;

import java.util.List;

public interface IRedisService<T>
{
    Iterable<T> findAllById(@NonNull Iterable<String> ids);

    void saveAll(@NonNull List<T> ids);
}
