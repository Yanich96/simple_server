package org.example;

public interface AccountService {
    void addNewUser(UserProfile userProfile);
    boolean authenticate(UserProfile userProfile);
}
