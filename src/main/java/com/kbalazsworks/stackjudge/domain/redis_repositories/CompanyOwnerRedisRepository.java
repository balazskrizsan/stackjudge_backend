package com.kbalazsworks.stackjudge.domain.redis_repositories;

import com.kbalazsworks.stackjudge.domain.entities.CompanyOwners;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyOwnerRedisRepository extends CrudRepository<CompanyOwners, String>
{
}
