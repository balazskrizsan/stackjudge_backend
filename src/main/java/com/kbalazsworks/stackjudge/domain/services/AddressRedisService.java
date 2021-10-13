package com.kbalazsworks.stackjudge.domain.services;

import com.kbalazsworks.stackjudge.domain.entities.Address;
import com.kbalazsworks.stackjudge.domain.redis_repositories.AddressRedisRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressRedisService implements IRedisService<Address>
{
    private final AddressRedisRepository addressRedisRepository;

    @Override
    public Iterable<Address> findAllById(@NonNull Iterable<String> ids)
    {
        return addressRedisRepository.findAllById(ids);
    }

    @Override
    public void saveAll(@NonNull List<Address> entities)
    {
        addressRedisRepository.saveAll(entities);
    }
}
