package com.kbalazsworks.stackjudge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

public abstract class AbstractE2eTest extends AbstractIntegrationTest
{
    @Autowired
    protected WebApplicationContext wac;

    public MockMvc getMockMvc()
    {
        return MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    public MockMvc getMockMvcWithSecurity()
    {
        return MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
    }
}

