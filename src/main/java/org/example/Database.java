package org.example;

import java.sql.ResultSet;

public interface Database {
    interface ResultMapper<T> {
        T map(ResultSet resultSet);
    }
    boolean execute(String sql);
    <T> T fetch(String sql, ResultMapper<T> mapper);

}
