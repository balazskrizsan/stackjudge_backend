package com.kbalazsworks.stackjudge.common.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class ConnectionService
{
    private final DataSource dataSource;
    private       Connection connection = null;

    public Connection getConnection() throws SQLException
    {
        if (null == connection)
        {
            this.connection = dataSource.getConnection();
        }

        return connection;
    }
}
