package com.kbalazsworks.stackjudge.mocking.setup_mock;

import com.kbalazsworks.stackjudge.domain.factories.PebbleTemplateFactory;
import com.kbalazsworks.stackjudge.mocking.MockCreator;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import lombok.SneakyThrows;

import java.io.StringWriter;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PebbleTemplateFactoryMocker
{
    private static PebbleTemplate templateMock;

    public static PebbleTemplateFactory create_returns_factoryMock_stores_templateMock(String testedTemplate)
    {
        PebbleTemplateFactory factoryMock = MockCreator.getPebbleTemplateFactoryMock();
        templateMock = MockCreator.getPebbleTemplateMock();
        when(factoryMock.create(testedTemplate)).thenReturn(templateMock);

        return factoryMock;
    }

    @SneakyThrows
    public static void verify_evaluate_stored_templateMock_withWriter()
    {
        verify(templateMock).evaluate(any(StringWriter.class));
        templateMock = null;
    }

    @SneakyThrows
    public static void verify_evaluate_stored_templateMock_withWriterAndContext(Map<String, Object> expectedContext)
    {
        verify(templateMock).evaluate(any(StringWriter.class), eq(expectedContext));
        templateMock = null;
    }
}
