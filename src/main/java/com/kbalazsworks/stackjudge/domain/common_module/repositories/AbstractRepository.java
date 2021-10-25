package com.kbalazsworks.stackjudge.domain.common_module.repositories;

import com.kbalazsworks.stackjudge.domain.common_module.services.JooqService;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
abstract public class AbstractRepository
{
    private JooqService jooqService;

    @Autowired
    public void setJooqService(JooqService jooqService)
    {
        this.jooqService = jooqService;
    }

    protected DSLContext getQueryBuilder()
    {
        return jooqService.getDbContext();
    }
}
