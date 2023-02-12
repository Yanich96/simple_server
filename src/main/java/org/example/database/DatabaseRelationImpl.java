package org.example.database;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
@Component
public class DatabaseRelationImpl implements Database {
    private static final Logger logger = LogManager.getLogger();

    public record Configuration(
            String driverClassName,
            String connectionString,
            String user,
            String password
    ) {
    }

    private Connection connection = null;

    @Autowired
    public DatabaseRelationImpl(Configuration configuration) {
        try {
            Class.forName(configuration.driverClassName);

            logger.info("Connecting to database...");

            var jdbc = new JdbcDataSource();
            jdbc.setURL(configuration.connectionString);
            jdbc.setUser(configuration.user);
            jdbc.setPassword(configuration.password);
            connection = jdbc.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        logger.info("Connecting database successfully");
    }


    @Override
    public boolean execute(String sql) {
        try (Statement st = connection.createStatement()) {
            st.execute(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public <T> T fetch(String sql, ResultMapper<T> mapper) {
        try (Statement st = connection.createStatement()) {
            var resultSet = st.executeQuery(sql);
            if (!resultSet.next())
                return null;
            else
                return mapper.map(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

