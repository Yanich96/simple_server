package org.example.services;


import org.example.UserProfile;

public interface AccountService {
    void signUpNewUser(UserProfile userProfile);
    boolean authenticate(UserProfile userProfile);
}
