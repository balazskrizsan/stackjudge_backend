package com.kbalazsworks.stackjudge.domain.services;

import com.kbalazsworks.stackjudge.domain.exceptions.PebbleException;
import com.kbalazsworks.stackjudge.domain.factories.PebbleTemplateFactory;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PebbleTemplateService
{
    private final PebbleTemplateFactory pebbleTemplateFactory;

    // @todo: test
    public String render(@NonNull String template, Map<String, Object> context) throws PebbleException
    {
        try
        {
            Writer writer = new StringWriter();
            // @todo: handle if context empty
            pebbleTemplateFactory.create(template).evaluate(writer, context);

            return writer.toString();
        }
        catch (Exception e)
        {
            throw new PebbleException("Pebble template error: " + e.getMessage());
        }
    }
}
