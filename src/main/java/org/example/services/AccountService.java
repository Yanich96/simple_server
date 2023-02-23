package org.example.services;

import org.example.UserProfile;

public interface AccountService {
    void signUpNewUser(UserProfile userProfile);

    String authenticate(UserProfile userProfile);

    void changePassword(String sessionId, String password);
}
