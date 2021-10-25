package com.kbalazsworks.stackjudge.domain.aws_module.factories;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import org.springframework.stereotype.Component;

@Component
public class AmazonSimpleEmailServiceFactory
{
    public AmazonSimpleEmailService create()
    {
        return AmazonSimpleEmailServiceClientBuilder.standard().withRegion(Regions.EU_CENTRAL_1).build();
    }
}
