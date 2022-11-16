package com.kbalazsworks.stackjudge.unit.common.services;

import com.kbalazsworks.stackjudge.AbstractTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Base64;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SecureRandomService_getUrlEncodedTest extends AbstractTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    @Test
    public void vintageHack()
    {
        assertTrue(true);
    }

    static class Provider_testingWithDifferentLength_checkedByProvider implements ArgumentsProvider
    {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context)
        {
            return Stream.of(
                Arguments.of(0, 0),
                Arguments.of(20, 20),
                Arguments.of(1000, 1000)
            );
        }
    }

    @ParameterizedTest(name = "{index} => testedLength={0}, expectedLength={1}")
    @ArgumentsSource(Provider_testingWithDifferentLength_checkedByProvider.class)
    public void testingWithDifferentLength_checkedByProvider(int testedLength, int expectedLength)
    {
        // Arrange
        // Act
        String actualEncodedRandom = serviceFactory.getSecureRandomService().getUrlEncoded(testedLength);
        String actualRandom        = new String(Base64.getDecoder().decode(actualEncodedRandom.getBytes()));

        // Assert
        assertThat(actualRandom).hasSize(expectedLength);
    }
}
