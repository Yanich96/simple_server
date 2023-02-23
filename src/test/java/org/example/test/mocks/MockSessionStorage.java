package org.example.test.mocks;

import org.example.database.SessionStorage;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@Primary
public class MockSessionStorage implements SessionStorage {
    private final HashMap<String, String> sessionToId = new HashMap<>();

    @Override
    public void set(String session, String userId) {
        sessionToId.put(session, userId);
    }

    @Override
    public String getUserId(String session) {
        return sessionToId.get(session);
    }
}
