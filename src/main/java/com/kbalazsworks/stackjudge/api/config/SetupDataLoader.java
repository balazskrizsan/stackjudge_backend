package com.kbalazsworks.stackjudge.api.config;

import com.kbalazsworks.stackjudge.api.model.Role;
import com.kbalazsworks.stackjudge.api.repo.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent>
{
    private boolean alreadySetup = false;

    @Autowired
    private RoleRepository roleRepository;

    @Override
//    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event)
    {
        if (alreadySetup)
        {
            return;
        }

        createRoleIfNotFound(Role.ROLE_USER);

        alreadySetup = true;
    }

    private Role createRoleIfNotFound(final String name)
    {
        return new Role(name);

//        Role role = roleRepository.findByName(name);
//        if (role == null)
//        {
//            role = new Role(name);
//        }
//
//        return roleRepository.save(role);
    }
}
