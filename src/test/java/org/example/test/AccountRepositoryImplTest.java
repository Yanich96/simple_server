package org.example.test;

import org.example.SessionFactoryConfiguration;
import org.example.UserProfile;
import org.example.database.AccountRepositoryImpl;
import org.example.database.DatabaseRelationImpl;
import org.example.exceptions.LoginConflictException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(
        classes = {
                AccountRepositoryImpl.class,
                DatabaseRelationImpl.class,
                SessionFactoryConfiguration.class
        }
)
@RunWith(SpringJUnit4ClassRunner.class)
public class AccountRepositoryImplTest {
    @Autowired
    AccountRepositoryImpl accountRepository;

    @Autowired
    SessionFactory sessionFactory;

    @Before
    public void init(){
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.createQuery("delete from UserProfile").executeUpdate();
        session.close();
    }

    @Test
    public void saveTest() {
        UserProfile user = new UserProfile("Cat2", "0809");
        Assert.assertNull(accountRepository.findByLogin(user.getLogin()));
        accountRepository.save(user);
        Assert.assertTrue(user.equals(accountRepository.findByLogin(user.getLogin())));
        Assert.assertThrows(LoginConflictException.class, () -> {
            accountRepository.save(new UserProfile("Cat2", "0809"));
        });
    }
}
