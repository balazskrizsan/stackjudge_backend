package com.kbalazsworks.stackjudge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public abstract class AbstractE2eTest extends AbstractIntegrationTest
{
    @Autowired
    private WebApplicationContext wac;

    public MockMvc getMockMvc()
    {
        return MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
}

