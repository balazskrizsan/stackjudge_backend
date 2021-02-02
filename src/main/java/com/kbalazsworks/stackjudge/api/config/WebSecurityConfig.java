package com.kbalazsworks.stackjudge.api.config;

import com.kbalazsworks.stackjudge.api.services.JWTAuthenticationFilterService;
import com.kbalazsworks.stackjudge.api.services.JWTAuthorizationFilterService;
import com.kbalazsworks.stackjudge.session.services.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.session.SessionManagementFilter;

import static com.kbalazsworks.stackjudge.api.config.SecurityConstants.SIGN_UP_URL;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    private final UserDetailsService    userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurityConfig(
        UserDetailsServiceImpl userDetailsService,
        BCryptPasswordEncoder bCryptPasswordEncoder
    )
    {
        this.userDetailsService    = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {

        http
            .addFilterBefore(new CorsFilterConfig(), SessionManagementFilter.class)
            .csrf().disable()
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
//            .antMatchers(HttpMethod.GET, ACTION_HEALTH_CHECK).permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilter(new JWTAuthenticationFilterService(authenticationManager()))
            .addFilter(new JWTAuthorizationFilterService(authenticationManager()));
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
}
