package com.kbalazsworks.stackjudge;

import com.kbalazsworks.stackjudge.spring_config.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
//@EnableSwagger2
//@EnableOpenApi
public class StackJudgeApplication
{
    private static List<String> clients             = Arrays.asList("google", "facebook");
    private static String       CLIENT_PROPERTY_KEY = "spring.security.oauth2.client.registration.";

    @Autowired
    private Environment env;

    public static void main(String[] args)
    {
        SpringApplication.run(StackJudgeApplication.class, args);
    }

    @Bean
    public ApplicationProperties applicationProperties()
    {
        return new ApplicationProperties();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Primary
    public DataSource dataSource()
    {
        ApplicationProperties applicationProperties = applicationProperties();

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(applicationProperties.getDataSourceDriverClassName());
        dataSource.setUrl(applicationProperties.getDataSourceUrl());
        dataSource.setUsername(applicationProperties.getDataSourceUsername());
        dataSource.setPassword(applicationProperties.getDataSourcePassword());

        return dataSource;
    }

    @Bean(name = "transactionManager")
    @Primary
    DataSourceTransactionManager getDataSourceTransactionManager()
    {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean(name = "transactionAwareDataSource")
    TransactionAwareDataSourceProxy getTransactionAwareDataSourceProxy()
    {
        return new TransactionAwareDataSourceProxy(dataSource());
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService()
    {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository());
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository()
    {
        List<ClientRegistration> registrations = clients.stream()
            .map(c -> getRegistration(c))
            .filter(registration -> registration != null)
            .collect(Collectors.toList());

        return new InMemoryClientRegistrationRepository(registrations);
    }

    private ClientRegistration getRegistration(String client)
    {
        String clientId = env.getProperty(
            CLIENT_PROPERTY_KEY + client + ".client-id");

        if (clientId == null)
        {
            return null;
        }

        String clientSecret = env.getProperty(
            CLIENT_PROPERTY_KEY + client + ".client-secret");

        if (client.equals("google"))
        {
            return CommonOAuth2Provider.GOOGLE.getBuilder(client)
                .clientId(clientId).clientSecret(clientSecret).build();
        }

        if (client.equals("facebook"))
        {
            return CommonOAuth2Provider.FACEBOOK.getBuilder(client)
                .clientId(clientId).clientSecret(clientSecret).build();
        }
        return null;
    }
}
