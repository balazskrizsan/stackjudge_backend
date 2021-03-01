package com.kbalazsworks.stackjudge.api.config;

import com.kbalazsworks.stackjudge.api.FacebookConnectionSignup;
import com.kbalazsworks.stackjudge.api.FacebookSignInAdapter;
import com.kbalazsworks.stackjudge.state.services.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    private final UserDetailsService    userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final FacebookConnectionSignup facebookConnectionSignup;

    public WebSecurityConfig(
        UserDetailsServiceImpl userDetailsService,
        BCryptPasswordEncoder bCryptPasswordEncoder,
        FacebookConnectionSignup facebookConnectionSignup
    )
    {
        this.userDetailsService    = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.facebookConnectionSignup = facebookConnectionSignup;
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
        // @formatter:off
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/login*","/signin/**","/signup/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin().loginPage("/login").permitAll()
            .and()
            .logout();
        // @formatter:on
    }

    @Bean
    // @Primary
    public ProviderSignInController providerSignInController()
    {
        ConnectionFactoryLocator  connectionFactoryLocator  = connectionFactoryLocator();
        UsersConnectionRepository usersConnectionRepository = getUsersConnectionRepository(connectionFactoryLocator);
        ((InMemoryUsersConnectionRepository) usersConnectionRepository).setConnectionSignUp(facebookConnectionSignup);

        return new ProviderSignInController(
            connectionFactoryLocator,
            usersConnectionRepository,
            new FacebookSignInAdapter()
        );
    }

    private ConnectionFactoryLocator connectionFactoryLocator()
    {
        ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();
        registry.addConnectionFactory(new FacebookConnectionFactory("", ""));

        return registry;
    }

    private UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator)
    {
        return new InMemoryUsersConnectionRepository(connectionFactoryLocator);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
}
