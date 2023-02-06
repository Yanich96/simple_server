package mocks;

import org.example.UserProfile;
import org.example.database.AccountRepository;

import java.util.HashMap;

public class MockAccountRepository implements AccountRepository {
    HashMap<String, String> usersMap = new HashMap<>();
    @Override
    public void save(UserProfile entity) {
        usersMap.put(entity.getLogin(), entity.getPassword());
    }

    @Override
    public UserProfile findByLogin(String login) {
        if(!usersMap.containsKey(login))
            return null;

        UserProfile user = new UserProfile(login, usersMap.get(login));
        return user;
    }

    public int countUsers()
    {
        return  usersMap.size();
    }
}
