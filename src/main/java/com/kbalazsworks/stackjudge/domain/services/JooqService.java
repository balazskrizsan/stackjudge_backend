package com.kbalazsworks.stackjudge.domain.services;

import lombok.NonNull;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.TransactionalRunnable;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;

@Service
public class JooqService
{
    private Connection connection;
    private DSLContext dslContext = null;

    @Autowired
    public void setConnection(ConnectionService connectionService) throws SQLException
    {
        this.connection = connectionService.getConnection();
    }

    public DSLContext getDbContext()
    {
        if (null == dslContext)
        {
            this.dslContext = DSL.using(connection, SQLDialect.POSTGRES);
        }

        return dslContext;
    }

    void transaction(@NonNull TransactionalRunnable transactional)
    {
        getDbContext().transaction(transactional);
    }
}
