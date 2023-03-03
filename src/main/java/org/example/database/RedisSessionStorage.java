package org.example.database;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.net.URI;

@Component
@PropertySource({"redis.properties"})
public class RedisSessionStorage implements SessionStorage {
    @Value("${redis.sessionTimeOutSek}")
    String timeOut;
    @Value(("${redis.url}"))
    String url;

    Jedis jedis = null;

    RedisSessionStorage() {
    }

    @PostConstruct
    void initialise() {
        jedis = new Jedis(URI.create(url));
    }

    @Override
    public void set(String session, String userId) {
        jedis.setex(session, Integer.parseInt(timeOut), userId);
    }

    @Override
    public String getUserId(String session) {
        return jedis.get(session);
    }
}
