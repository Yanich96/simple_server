package org.example;

import java.util.HashMap;
import java.util.Map;


public class AccountService {
    private Map<String, UserProfile> loginToProfile;

    public AccountService()
    {
        loginToProfile = new HashMap<>();
    }

    public void addNewUser(UserProfile userProfile)
    {
        loginToProfile.put(userProfile.getLogin(), userProfile);
    }

    public boolean findUser(UserProfile userProfile)
    {
        return loginToProfile.get(userProfile.getLogin()).getPassword().equals(userProfile.getPassword());
    }
}
