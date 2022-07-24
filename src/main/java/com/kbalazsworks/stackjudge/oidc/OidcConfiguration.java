package com.kbalazsworks.stackjudge.oidc;

import com.kbalazsworks.stackjudge.oidc.exceptions.OidcApiException;
import com.kbalazsworks.stackjudge.oidc.factories.OidcServiceFactory;
import com.kbalazsworks.stackjudge.oidc.services.IOidcService;
import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class OidcConfiguration
{
    private final OidcServiceFactory oidcServiceFactory;
    private final ApplicationProperties applicationProperties;

    @Bean
    public IOidcService oidcService() throws OidcApiException
    {
        return oidcServiceFactory.create(applicationProperties.getSjIdsFullHost());
    }
}
