package org.example.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.UserProfile;
import org.example.database.AccountRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountServiceImpl implements AccountService {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private SessionFactory sessions;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void signUpNewUser(UserProfile userProfile) {
        accountRepository.save(userProfile);
    }

    @Override
    public boolean authenticate(UserProfile userProfile) {
        UserProfile fetchedUserProfile = accountRepository.findByLogin(userProfile.getLogin());
        return fetchedUserProfile != null &&
                    fetchedUserProfile.getPassword() != null && userProfile.getPassword() != null &&
                    fetchedUserProfile.getPassword().equals(userProfile.getPassword());
    }
}
