package org.example.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.UserProfile;
import org.example.exceptions.ConflictUniqueNameException;
import org.example.exceptions.LoginConflictException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountRepositoryImpl implements AccountRepository {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private Database database;

    @Override
    public void save(UserProfile entity) {
        logger.info("Starting insert user into table...");
        try {
            database.persist(entity);
            logger.info("User " + entity.getLogin() + " inserted successfully");
        } catch (ConstraintViolationException e) {
            throw new LoginConflictException(entity.getLogin());
        }
    }

    @Override
    public UserProfile findByLogin(String login) {
        String hql = "from UserProfile where login = :login";
        logger.info("Selecting user");
        var users = database.execute(session -> {
            return session.createQuery(hql).setParameter("login", login).list();
        });

        if (users.size() > 1) throw new ConflictUniqueNameException("login");
        if (users.isEmpty()) {
            logger.info("There is not user in Table");
            return null;
        }
        logger.info("User is selected");
        return (UserProfile) users.get(0);
    }

    @Override
    public void changePassword(long id, String password) {
        logger.info("Starting update user password into table...");
        String hql = "update UserProfile u set u.password =: password where id = :id";
        database.execute(session -> {
            session.createQuery(hql)
                    .setParameter("id", id)
                    .setParameter("password", password).executeUpdate();
            return null;
        });
    }
}
