package org.example.database;

import org.example.UserProfile;

public interface AccountRepository {
    void save(UserProfile entity);
    UserProfile findByLogin(String login);
}
