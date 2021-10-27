package com.kbalazsworks.stackjudge.api.repositories;

import com.kbalazsworks.stackjudge.api.entities.RegistrationSecret;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationSecretRepository extends CrudRepository<RegistrationSecret, String>
{
}
