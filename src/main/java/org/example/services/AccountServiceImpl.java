package org.example.services;

import org.example.database.AccountRepository;
import org.example.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void addNewUser(UserProfile userProfile) {
        accountRepository.save(userProfile);
    }

    @Override
    public boolean authenticate(UserProfile userProfile) {
        var fetchedUserProfile = accountRepository.findByLogin(userProfile.getLogin());
        return fetchedUserProfile != null && userProfile != null &&
                fetchedUserProfile.getPassword() != null && userProfile.getPassword() != null &&
                fetchedUserProfile.getPassword().equals(userProfile.getPassword());
    }
}
