package com.kbalazsworks.stackjudge.domain.map_module.services;

import com.kbalazsworks.stackjudge.domain.map_module.entities.GoogleStaticMapsCache;
import com.kbalazsworks.stackjudge.domain.map_module.repositories.GoogleStaticMapsCacheRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoogleStaticMapsCacheService
{
    private final GoogleStaticMapsCacheRepository googleStaticMapsCacheRepository;

    public GoogleStaticMapsCache getByHash(String hash)
    {
        return googleStaticMapsCacheRepository.getByHash(hash);
    }

    public void create(GoogleStaticMapsCache googleStaticMapsCache)
    {
        googleStaticMapsCacheRepository.create(googleStaticMapsCache);
    }
}
