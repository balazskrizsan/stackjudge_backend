package com.kbalazsworks.stackjudge.domain.redis_repositories;

import com.kbalazsworks.stackjudge.domain.entities.CompanyAddresses;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRedisRepository extends CrudRepository<CompanyAddresses, String>
{
}
