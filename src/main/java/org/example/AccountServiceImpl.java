package org.example;

import java.sql.SQLException;


public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    public AccountServiceImpl() {
        this.accountRepository = Context.getContext().get(AccountRepository.class);
    }

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
