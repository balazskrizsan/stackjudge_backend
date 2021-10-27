package com.kbalazsworks.stackjudge.domain.address_module.repositories;

import com.kbalazsworks.stackjudge.domain.address_module.entities.CompanyAddresses;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRedisRepository extends CrudRepository<CompanyAddresses, String>
{
}
