package org.example.services;


import org.example.UserProfile;

public interface AccountService {
    void addNewUser(UserProfile userProfile);
    boolean authenticate(UserProfile userProfile);
}
