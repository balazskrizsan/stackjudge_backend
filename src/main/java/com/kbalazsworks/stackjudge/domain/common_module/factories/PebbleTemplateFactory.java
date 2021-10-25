package com.kbalazsworks.stackjudge.domain.common_module.factories;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.template.PebbleTemplate;
import org.springframework.stereotype.Component;

@Component
public class PebbleTemplateFactory
{
    public PebbleTemplate create(String template)
    {
        return new PebbleEngine.Builder().build().getTemplate("pebble_templates/" + template);
    }
}
