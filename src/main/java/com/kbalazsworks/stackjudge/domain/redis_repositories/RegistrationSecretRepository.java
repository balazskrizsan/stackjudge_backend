package com.kbalazsworks.stackjudge.domain.redis_repositories;

import com.kbalazsworks.stackjudge.domain.entities.RegistrationSecret;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationSecretRepository extends CrudRepository<RegistrationSecret, String>
{
}
