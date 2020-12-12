package com.kbalazsworks.stackjudge.api.repo;

import com.kbalazsworks.stackjudge.api.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByName(String name);
}
