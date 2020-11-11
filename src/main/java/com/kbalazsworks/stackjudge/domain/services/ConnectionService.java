package com.kbalazsworks.stackjudge.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Service
public class ConnectionService
{
    private DataSource dataSource;
    private Connection connection = null;

    @Autowired
    public void setDataSource(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    public Connection getConnection() throws SQLException
    {
        if (null == connection)
        {
            this.connection = dataSource.getConnection();
        }

        return connection;
    }
}
