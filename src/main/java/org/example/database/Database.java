package org.example.database;

import org.hibernate.Session;

import java.util.List;

public interface Database {
    interface Handler<T> {
        List<T> handle(Session session);
    }
    void persist(Object entity);
    <T> List<T> execute(Handler<T> handler);

}
