package org.example.database;

public interface SessionStorage {
    void set(String session, String userId);

    String getUserId(String session);
}
