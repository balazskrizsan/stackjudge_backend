package com.kbalazsworks.stackjudge;

import com.kbalazsworks.stackjudge.db.tables.*;
import com.kbalazsworks.stackjudge.domain.services.JooqService;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractIntegrationTest extends AbstractTest
{
    protected final Company companyTable = Company.COMPANY;
    protected final Address addressTable = Address.ADDRESS;
    protected final Group   groupTable   = Group.GROUP;

    @Autowired
    private JooqService jooqService;

    protected DSLContext getQueryBuilder()
    {
        return jooqService.getDbContext();
    }
}
