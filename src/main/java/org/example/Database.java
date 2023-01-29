package org.example;


import org.h2.jdbc.JdbcSQLSyntaxErrorException;
import org.h2.jdbcx.JdbcDataSource;

import javax.xml.crypto.Data;
import java.sql.*;

public class Database {
    private Connection connection = null;

    public Database() {
        try {
            Class.forName("org.h2.Driver");

            System.out.println("Connecting to database...");

            var jdbc = new JdbcDataSource();
            jdbc.setURL("jdbc:h2:./h2db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
            jdbc.setUser("test");
            jdbc.setPassword("test");
            connection = jdbc.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Connecting database successfully");


        System.out.println("Creating table in given database...");
        try(Statement stmt = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS REGISTRATION   " +
                    "(id bigint auto_increment, " +
                    " login VARCHAR(256), " +
                    " password VARCHAR(256), " +
                    " primary key ( id ))";
            stmt.execute(sql);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void insertToDB(UserProfile user) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            String sql = "INSERT INTO REGISTRATION (login, password) " + "VALUES ( '" + user.getLogin() +"','"+ user.getPassword() + "')";
            stmt.execute(sql);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean checkUser(UserProfile user)
    {
        try (Statement stmt = connection.createStatement()) {
            String sql = "SELECT password FROM REGISTRATION WHERE login='"+user.getLogin() + "'";
            try (ResultSet rs =  stmt.executeQuery(sql)){
                if(!rs.next())
                    return false;
                String passwordUser = rs.getString("password");
                return user.getPassword().equals(passwordUser);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

}

