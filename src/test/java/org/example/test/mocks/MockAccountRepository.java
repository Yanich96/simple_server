package org.example.test.mocks;

import org.example.UserProfile;
import org.example.database.AccountRepository;
import org.example.exceptions.LoginConflictException;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Primary
public class MockAccountRepository implements AccountRepository {
    private final HashMap<String, String> usersMapLoginPassword = new HashMap<>();
    private final HashMap<Long, String> usersMapIdLogin = new HashMap<>();

    @Override
    public void save(UserProfile entity) {
        if (usersMapLoginPassword.containsKey(entity.getLogin()))
            throw new LoginConflictException("");
        usersMapLoginPassword.put(entity.getLogin(), entity.getPassword());
        usersMapIdLogin.put(entity.getId(), entity.getLogin());
    }

    @Override
    public UserProfile findByLogin(String login) {
        if (!usersMapLoginPassword.containsKey(login))
            return null;

        return new UserProfile(login, usersMapLoginPassword.get(login));
    }

    @Override
    public void changePassword(long id, String password) {
        String login = usersMapIdLogin.get(id);
        usersMapLoginPassword.put(login, password);
    }

    public int countUsers() {
        return usersMapLoginPassword.size();
    }

    public void reset() {
        usersMapLoginPassword.clear();
        usersMapIdLogin.clear();
    }
}
