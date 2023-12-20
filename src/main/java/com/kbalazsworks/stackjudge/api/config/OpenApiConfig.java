package com.kbalazsworks.stackjudge.api.config;

import com.kbalazsworks.stackjudge.api.controllers.account_controller.AccountConfig;
import com.kbalazsworks.stackjudge.api.controllers.company_controller.CompanyConfig;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Stream;

@Configuration
public class OpenApiConfig
{
    @Bean
    public GroupedOpenApi frontendApi()
    {

        return GroupedOpenApi
            .builder()
            .group("frontend")
            .pathsToMatch(
                Stream.concat(
                    AccountConfig.openapiFrontendUrls.stream(),
                    CompanyConfig.openapiFrontendUrls.stream()
                ).toArray(String[]::new)
            )

            .build();
    }
}
