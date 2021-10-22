package com.kbalazsworks.stackjudge.domain.services;

import com.kbalazsworks.stackjudge.domain.entities.Address;
import lombok.NonNull;

import java.util.List;

// @todo3: abstract helpers
public interface IRedisService<T>
{
    List<T> findAllById(@NonNull List<Long> ids);

    void saveAll(@NonNull List<T> ids);
}
