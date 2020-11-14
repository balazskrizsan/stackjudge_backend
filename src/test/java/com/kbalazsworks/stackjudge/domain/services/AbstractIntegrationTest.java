package com.kbalazsworks.stackjudge.domain.services;

import com.kbalazsworks.stackjudge.domain.AbstractTest;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractIntegrationTest extends AbstractTest
{
    protected final com.kbalazsworks.stackjudge.db.tables.Company companyTable
        = com.kbalazsworks.stackjudge.db.tables.Company.COMPANY;

    protected final com.kbalazsworks.stackjudge.db.tables.Address addressTable
        = com.kbalazsworks.stackjudge.db.tables.Address.ADDRESS;

    @Autowired
    private JooqService jooqService;

    protected DSLContext getQueryBuilder()
    {
        return jooqService.createQueryBuilder();
    }
}
