package org.example;


import org.h2.jdbcx.JdbcDataSource;

import java.sql.*;

public class DatabaseRelationImpl implements Database{


    private Connection connection = null;

    public DatabaseRelationImpl() {
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
            String sql = "CREATE TABLE IF NOT EXISTS Account   " +
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

