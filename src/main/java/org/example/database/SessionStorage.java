package org.example.database;

public interface SessionStorage {
    public void set(String session, String userId);

    public String getUserId(String session);
}
