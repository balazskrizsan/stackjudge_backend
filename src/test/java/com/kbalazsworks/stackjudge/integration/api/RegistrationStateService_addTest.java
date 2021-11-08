package com.kbalazsworks.stackjudge.integration.api;

import com.kbalazsworks.stackjudge.AbstractIntegrationTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.api.repositories.RegistrationSecretRepository;
import lombok.SneakyThrows;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class RegistrationStateService_addTest extends AbstractIntegrationTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    @Autowired
    private RegistrationSecretRepository registrationSecretRepository;

    @Test
    public void addWith2HoursExpiry_willStored()
    {
        // Arrange
        String testedState   = "AAABBBCCC";
        int    testedHours   = 2;
        String expectedState = "AAABBBCCC";

        // Act
        serviceFactory.getRegistrationStateService().add(testedState, testedHours);

        // Assert
        assertThat(registrationSecretRepository.existsById(expectedState)).isTrue();
        //@todo: move to AOP annotation
        getRedisConnection().flushAll();
    }

    @Test
    @Ignore("Redis timout not working")
    public void addWith0HoursExpiry_willStored()
    {
        // Arrange
        String testedState   = "AAABBBCCC";
        int    testedHours   = 0;
        String expectedState = "AAABBBCCC";

        // Act
        serviceFactory.getRegistrationStateService().add(testedState, testedHours);

        // Assert
        assertThat(registrationSecretRepository.existsById(expectedState)).isFalse();
        //@todo: move to AOP annotation
        getRedisConnection().flushAll();
    }
}
