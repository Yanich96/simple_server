package org.example.database;

import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
@Component
public class AccountRepositoryImpl implements AccountRepository{
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private Database database;

    @PostConstruct
    public void initialize() {
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS Account   " + "(id bigint auto_increment, " + " login VARCHAR(256), " + " password VARCHAR(256), " + " primary key ( id ))";
        logger.info("Table creating...");
        try {
            database.execute(sql);
            logger.info("Table created");
        }
        catch (Exception e)
        {
            logger.error(e);
            throw e;
        }

    }

    @Override
    public void save(UserProfile entity) {
        String sql = "INSERT INTO Account (login, password) " + "VALUES ( '" + entity.getLogin() + "','" + entity.getPassword() + "')";
        logger.info("Starting insert user into table...");
        try {
            database.execute(sql);
            logger.info("Inserted successfully");
        }
        catch (Exception e)
        {
            logger.error(e);
            throw e;
        }

    }
    @Override
    public UserProfile findByLogin(String login) {
        String sql = "SELECT login, password FROM Account WHERE login='" + login + "'";
        logger.info("Selecting user");
        return database.fetch(sql, resultSet -> {
            try {
                logger.info("Selected successfully");
                return new UserProfile(resultSet.getString("login"), resultSet.getString("password"));
            } catch (SQLException e) {
                logger.error(e);
                throw new RuntimeException(e);
            }
        });
    }
}
