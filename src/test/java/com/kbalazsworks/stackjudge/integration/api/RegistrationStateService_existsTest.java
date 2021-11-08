package com.kbalazsworks.stackjudge.integration.api;

import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.api.repositories.RegistrationSecretRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class RegistrationStateService_existsTest extends AbstractIntegrationTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    @Autowired
    private RegistrationSecretRepository registrationSecretRepository;

    @Test
    public void withNotExistingToken_returnsFalse()
    {
        // Arrange
        //@todo: move to AOP annotation
        getRedisConnection().flushAll();
        String  testedState       = "not existing state";
        boolean expectedExistence = false;

        // Act
        boolean actual = serviceFactory.getRegistrationStateService().exists(testedState);

        // Assert
        assertThat(actual).isEqualTo(expectedExistence);
        //@todo: move to AOP annotation
        getRedisConnection().flushAll();
    }

    @Test
    public void withExistingToken_returnsTrue()
    {
        // Arrange
        //@todo: move to AOP annotation
        getRedisConnection().flushAll();
        String  testedState       = "existing state";
        String  insertedState     = "existing state";
        boolean expectedExistence = true;

        serviceFactory.getRegistrationStateService().add(insertedState, 1);

        // Act
        boolean actual = serviceFactory.getRegistrationStateService().exists(testedState);

        // Assert
        assertThat(actual).isEqualTo(expectedExistence);
        //@todo: move to AOP annotation
        getRedisConnection().flushAll();
    }
}
