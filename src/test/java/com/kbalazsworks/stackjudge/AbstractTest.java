package com.kbalazsworks.stackjudge;

import org.junit.platform.commons.JUnitException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application.properties")
@ContextConfiguration(classes = StackJudgeApplication.class)
public abstract class AbstractTest
{
    public JUnitException getRepeatException(int repetition)
    {
        return new JUnitException("Missing test data on repetition#" + repetition);
    }
}
