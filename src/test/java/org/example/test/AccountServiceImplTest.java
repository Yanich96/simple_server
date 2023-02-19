package org.example.test;

import org.example.SessionFactoryConfiguration;
import org.example.test.mocks.MockAccountRepository;
import org.example.UserProfile;
import org.example.services.AccountService;
import org.example.services.AccountServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@ContextConfiguration(
        classes = {
                MockAccountRepository.class,
                AccountServiceImpl.class,
                SessionFactoryConfiguration.class
        }
)
@RunWith(SpringJUnit4ClassRunner.class)
public class AccountServiceImplTest {
    @Autowired
    AccountService accountService;
    @Autowired
    MockAccountRepository mockAccountRepository;

    @Before
    public void init()
    {
        mockAccountRepository.reset();
    }

    @Test
    public void addNewUserTest() {
        UserProfile userProfile = new UserProfile("fryChicken", "qwerty");
        Assert.assertEquals(0, mockAccountRepository.countUsers());
        accountService.signUpNewUser(userProfile);
        Assert.assertEquals(1, mockAccountRepository.countUsers());
    }

    @Test
    public void findByLoginTest() {
        UserProfile userProfile = new UserProfile("fryChicken11", "qwerty11");
        Assert.assertFalse(accountService.authenticate(userProfile));
        mockAccountRepository.save(userProfile);
        Assert.assertTrue(accountService.authenticate(userProfile));

        UserProfile userProfile2 = new UserProfile("Chicken", "qwerty123");
        Assert.assertFalse(accountService.authenticate(userProfile2));
        mockAccountRepository.save(userProfile2);
        Assert.assertTrue(accountService.authenticate(userProfile2));
        Assert.assertTrue(accountService.authenticate(userProfile));
    }
}
