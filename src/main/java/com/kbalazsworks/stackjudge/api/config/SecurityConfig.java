package com.kbalazsworks.stackjudge.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

@Configuration
@EnableWebSecurity
@PropertySource("classpath:application.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Autowired
    ClientRegistrationRepository  clientRegistrationRepository;
    @Autowired
    OAuth2AuthorizedClientService authorizedClientService;

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.authorizeRequests().anyRequest().authenticated()
            .and()
            .oauth2Login()
            .clientRegistrationRepository(clientRegistrationRepository)
            .authorizedClientService(authorizedClientService);
    }
}
