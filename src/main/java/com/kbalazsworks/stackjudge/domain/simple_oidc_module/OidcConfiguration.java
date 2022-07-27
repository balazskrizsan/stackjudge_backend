package com.kbalazsworks.stackjudge.domain.simple_oidc_module;

import com.kbalazsworks.simple_oidc.services.IOidcService;
import com.kbalazsworks.simple_oidc.services.OidcService;
import io.activej.inject.Injector;
import io.activej.inject.module.AbstractModule;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class OidcConfiguration
{
    @Bean
    public IOidcService oidcService()
    {
        AbstractModule module = new com.kbalazsworks.simple_oidc.Configuration().setUpDi();

        return Injector.of(module).getInstance(OidcService.class);
    }
}
