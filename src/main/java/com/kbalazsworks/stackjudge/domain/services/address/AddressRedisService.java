package com.kbalazsworks.stackjudge.domain.services.address;

import com.google.common.collect.ImmutableList;
import com.kbalazsworks.stackjudge.domain.entities.Address;
import com.kbalazsworks.stackjudge.domain.entities.CompanyAddresses;
import com.kbalazsworks.stackjudge.domain.redis_repositories.AddressRedisRepository;
import com.kbalazsworks.stackjudge.domain.services.IRedisService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressRedisService implements IRedisService<CompanyAddresses>
{
    private final AddressRedisRepository addressRedisRepository;

    @Override
    public List<CompanyAddresses> findAllById(@NonNull List<Long> ids)
    {
        List<String> stringIds = ids.stream().map(Object::toString).collect(Collectors.toList());

        return ImmutableList.copyOf(addressRedisRepository.findAllById(stringIds));
    }

    @Override
    public void saveAll(@NonNull List<CompanyAddresses> entities)
    {
        addressRedisRepository.saveAll(entities);
    }
}
