package org.example.database;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseRelationImpl implements Database {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    private SessionFactory sessions;

    @Override
    public void persist(Object entity) {
        Session session = sessions.getCurrentSession();
        var txn = session.beginTransaction();
        logger.info("Session is opened");
        try {
            session.persist(entity);
            session.getTransaction().commit();
        } finally {
            if (txn.isActive())
                txn.rollback();
            logger.info("Session is closed");
        }
    }

    @Override
    public <T> List<T> execute(Handler<T> handler) {
        Session session = sessions.getCurrentSession();
        var txn = session.beginTransaction();
        logger.info("Session is opened");
        try {
            var result = handler.handle(session);
            session.getTransaction().commit();
            return result;
        } finally {
            if (txn.isActive())
                txn.rollback();
            logger.info("Session is closed");
        }
    }
}

