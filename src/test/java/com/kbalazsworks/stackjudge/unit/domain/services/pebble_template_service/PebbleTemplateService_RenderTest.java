package com.kbalazsworks.stackjudge.unit.domain.services.pebble_template_service;

import com.kbalazsworks.stackjudge.AbstractTest;
import com.kbalazsworks.stackjudge.ServiceFactory;
import com.kbalazsworks.stackjudge.mocking.setup_mock.PebbleTemplateFactoryMocker;
import lombok.SneakyThrows;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class PebbleTemplateService_RenderTest extends AbstractTest
{
    @Autowired
    private ServiceFactory serviceFactory;

    @Test
    @SneakyThrows
    public void validRequest_callsEvaluateWithOneParameter()
    {
        // Arrange
        String              testedTemplate = "test.tpl";
        Map<String, Object> testedContext  = null;

        // Act
        String actualMockedTemplate = serviceFactory.getPebbleTemplateService(
                PebbleTemplateFactoryMocker.create_returns_factoryMock_stores_templateMock(testedTemplate)
            )
            .render(testedTemplate, testedContext);

        // Assert
        assertAll(
            () -> assertThat(actualMockedTemplate).isEqualTo(""),
            PebbleTemplateFactoryMocker::verify_evaluate_stored_templateMock_withWriter
        );
    }

    @Test
    @SneakyThrows
    public void validRequest_callsEvaluateWithTwoParameter()
    {
        // Arrange
        String testedTemplate = "test.tpl";
        Map<String, Object> testedContext = new HashMap<>()
        {{
            put("this", "that");
        }};
        Map<String, Object> expectedContext = new HashMap<>()
        {{
            put("this", "that");
        }};

        // Act
        String actualMockedTemplate = serviceFactory.getPebbleTemplateService(
                PebbleTemplateFactoryMocker.create_returns_factoryMock_stores_templateMock(testedTemplate)
            )
            .render(testedTemplate, testedContext);

        // Assert
        assertAll(
            () -> assertThat(actualMockedTemplate).isEqualTo(""),
            () -> PebbleTemplateFactoryMocker.verify_evaluate_stored_templateMock_withWriterAndContext(expectedContext)
        );
    }
}
