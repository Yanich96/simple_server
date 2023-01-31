package org.example;

import java.sql.SQLException;

public class AccountRepository {

    private Database database = null;

    AccountRepository() {
        this.database = (Database) Context.getContext().get(Database.class);
    }

    public void save(UserProfile entity) {
        String sql = "INSERT INTO Account (login, password) " + "VALUES ( '" + entity.getLogin() + "','" + entity.getPassword() + "')";
        database.execute(sql);
    }

    public UserProfile findByLogin(String login) {
        String sql = "SELECT login, password FROM Account WHERE login='" + login + "'";
        return database.fetch(sql, resultSet -> {
            try {
                return new UserProfile(resultSet.getString("login"), resultSet.getString("password"));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
