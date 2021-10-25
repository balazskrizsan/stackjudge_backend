package com.kbalazsworks.stackjudge.domain.common_module.services;

import com.kbalazsworks.stackjudge.domain.common_module.exceptions.PebbleException;
import com.kbalazsworks.stackjudge.domain.common_module.factories.PebbleTemplateFactory;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PebbleTemplateService
{
    private final PebbleTemplateFactory pebbleTemplateFactory;

    public String render(@NonNull String template, @Nullable Map<String, Object> context) throws PebbleException
    {
        try
        {
            Writer writer = new StringWriter();
            if (null == context)
            {
                pebbleTemplateFactory.create(template).evaluate(writer);
            }
            else
            {
                pebbleTemplateFactory.create(template).evaluate(writer, context);
            }

            return writer.toString();
        }
        // @todo3: test
        catch (Exception e)
        {
            throw new PebbleException("Pebble template error: " + e.getMessage());
        }
    }
}
