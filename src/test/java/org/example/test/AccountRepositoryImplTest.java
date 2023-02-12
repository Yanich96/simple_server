package org.example.test;

import org.example.SpringContextConfiguration;
import org.example.UserProfile;
import org.example.database.AccountRepositoryImpl;
import org.example.database.DatabaseRelationImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(
        classes = {
                AccountRepositoryImpl.class,
                DatabaseRelationImpl.class,
                SpringContextConfiguration.class
        }
)
@RunWith(SpringJUnit4ClassRunner.class)
public class AccountRepositoryImplTest {
    @Autowired
    AccountRepositoryImpl accountRepository;

    @Test
    public void saveTest() {
        UserProfile user = new UserProfile("Cat", "0809");
        Assert.assertNull(accountRepository.findByLogin(user.getLogin()));
        accountRepository.save(user);
        Assert.assertTrue(user.equals(accountRepository.findByLogin(user.getLogin())));
    }
}
