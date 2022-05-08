package com.kbalazsworks.stackjudge.api.config;

import com.kbalazsworks.stackjudge.api.controllers.account_controller.AccountConfig;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig
{
    @Bean
    public GroupedOpenApi frontendApi()
    {
        return GroupedOpenApi
            .builder()
            .group("frontend")
            .pathsToMatch(AccountConfig.openapiFrontendUrls.toArray(String[]::new))
            .build();
    }
}
