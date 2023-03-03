package org.example.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.UserProfile;
import org.example.database.AccountRepository;
import org.example.database.SessionStorage;
import org.example.exceptions.SessionNotFoundException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AccountServiceImpl implements AccountService {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private SessionFactory sessions;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private SessionStorage sessionStorage;

    @Override
    public void signUpNewUser(UserProfile userProfile) {
        accountRepository.save(userProfile);
    }

    @Override
    public String authenticate(UserProfile userProfile) {
        UserProfile fetchedUserProfile = accountRepository.findByLogin(userProfile.getLogin());
        String sessionId = UUID.randomUUID().toString();
        if (fetchedUserProfile != null &&
                fetchedUserProfile.getPassword() != null && userProfile.getPassword() != null &&
                fetchedUserProfile.getPassword().equals(userProfile.getPassword())) {
            logger.info("Saving user sessionId...");
            sessionStorage.set(sessionId, String.valueOf(fetchedUserProfile.getId()));
            logger.info("User sessionId was saved");
            return sessionId;
        }
        return null;
    }

    @Override
    public void changePassword(String sessionId, String password) {
        logger.info("Getting the user Id..");
        String session = sessionStorage.getUserId(sessionId);
        if (session == null) {
            logger.info("User Id was not found");
            throw new SessionNotFoundException("Session was not found");
        }
        long userId = Long.parseLong(session);
        logger.info("User Id was gotten successfully");
        accountRepository.changePassword(userId, password);
    }

    @Override
    public long getId(String sessionId){
        logger.info("Getting the user Id..");
        String session = sessionStorage.getUserId(sessionId);
        if (session == null) {
            logger.info("User Id was not found");
            throw new SessionNotFoundException("Session was not found");
        }
        long userId = Long.parseLong(session);
        logger.info("User Id was gotten successfully");
        return userId;
    }
}
