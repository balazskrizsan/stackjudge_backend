package com.kbalazsworks.stackjudge.integration.api;

import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.api.repositories.RegistrationSecretRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class RegistrationStateService_deleteTest extends AbstractIntegrationTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    @Autowired
    private RegistrationSecretRepository registrationSecretRepository;

    @Test
    public void deleteExistingState_removedFromRedis()
    {
        // Arrange
        //@todo: move to AOP annotation
        getRedisConnection().flushAll();
        String testedState   = "existing state";
        String insertedState = "existing state";
        String deletedState  = "existing state";

        serviceFactory.getRegistrationStateService().add(insertedState, 1);

        // Act
        serviceFactory.getRegistrationStateService().delete(testedState);

        // Assert
        assertThat(registrationSecretRepository.existsById(deletedState)).isFalse();
        //@todo: move to AOP annotation
        getRedisConnection().flushAll();
    }
}
