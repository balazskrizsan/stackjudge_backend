package com.kbalazsworks.stackjudge.domain.redis_repositories;

import com.kbalazsworks.stackjudge.domain.entities.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRedisRepository extends CrudRepository<Address, String>
{
}
