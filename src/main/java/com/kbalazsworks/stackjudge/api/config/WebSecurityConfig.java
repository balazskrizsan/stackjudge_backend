package com.kbalazsworks.stackjudge.api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kbalazsworks.simple_oidc.services.ICommunicationService;
import com.kbalazsworks.stackjudge.api.controllers.account_controller.AccountConfig;
import com.kbalazsworks.stackjudge.api.controllers.company_controller.CompanyConfig;
import com.kbalazsworks.stackjudge.api.controllers.group_controller.GroupConfig;
import com.kbalazsworks.stackjudge.api.controllers.maps_controller.MapsConfig;
import com.kbalazsworks.stackjudge.api.controllers.test_controller.TestConfig;
import com.kbalazsworks.stackjudge.api.services.JWTAuthenticationFilterService;
import com.kbalazsworks.stackjudge.api.services.JWTAuthorizationFilterService;
import com.kbalazsworks.stackjudge.api.value_objects.ResponseData;
import com.kbalazsworks.stackjudge.state.services.AccountService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.session.SessionManagementFilter;

import static com.kbalazsworks.stackjudge.api.config.SecurityConstants.SIGN_UP_URL;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
@Log4j2
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    private final AccountService        accountService;
    private final ICommunicationService communicationService;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(@NonNull HttpSecurity http) throws Exception
    {
        // @formatter:off
        http
            .addFilterBefore(new CorsFilterConfig(), SessionManagementFilter.class)

            .csrf()
            .disable()

            .authorizeRequests()

            .antMatchers(POST, SIGN_UP_URL).permitAll()

            .antMatchers(GET, CompanyConfig.CONTROLLER_URI + CompanyConfig.GET_SECURITY_PATH).permitAll()
            .antMatchers(GET, CompanyConfig.CONTROLLER_URI + CompanyConfig.SEARCH_SECURITY_PATH).permitAll()

            .antMatchers(GET, AccountConfig.CONTROLLER_URI + AccountConfig.REGISTRATION_AND_LOGIN_SECURITY_PATH).permitAll()
            .antMatchers(GET, AccountConfig.CONTROLLER_URI + AccountConfig.FACEBOOK_CALLBACK_SECURITY_PATH).permitAll()
            .antMatchers(GET, AccountConfig.CONTROLLER_URI + AccountConfig.GET_PUSHOVER_TOKEN_BY_USER_ID_SECURITY_PATH).permitAll()

            .antMatchers(POST, GroupConfig.CONTROLLER_URI + GroupConfig.POST_SECURITY_PATH).permitAll()

            .antMatchers(POST, MapsConfig.CONTROLLER_URI + MapsConfig.POST_SECURITY_PATH).permitAll()

            .antMatchers(GET, TestConfig.CONTROLLER_URI + TestConfig.NOT_PROTECTED_SECURITY_PATH).permitAll()

            .anyRequest().authenticated()

            .and()

            .addFilter(new JWTAuthenticationFilterService(authenticationManager()))
            .addFilter(new JWTAuthorizationFilterService(authenticationManager(), accountService, communicationService))

            .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());

        http
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.NEVER);
        // @formatter:on
    }

    private AuthenticationEntryPoint authenticationEntryPoint()
    {
        return (request, response, e) -> {
            log.error("Authentication error", e);
            //@todo: add request id
            ResponseData<String> errorResponse = new ResponseData<>("User not authenticated", true, 2, "0");

            response.getWriter().append(new ObjectMapper().writeValueAsString(errorResponse));
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(401);
        };
    }
}
