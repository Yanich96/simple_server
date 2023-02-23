package org.example.test;

import org.example.SessionFactoryConfiguration;
import org.example.exceptions.SessionNotFoundException;
import org.example.test.mocks.MockAccountRepository;
import org.example.UserProfile;
import org.example.services.AccountService;
import org.example.services.AccountServiceImpl;
import org.example.test.mocks.MockSessionStorage;
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
                SessionFactoryConfiguration.class,
                MockSessionStorage.class
        }
)
@RunWith(SpringJUnit4ClassRunner.class)
public class AccountServiceImplTest {
    @Autowired
    AccountService accountService;
    @Autowired
    MockAccountRepository mockAccountRepository;
    @Autowired
    MockSessionStorage mockSessionStorage;

    @Before
    public void init() {
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
        Assert.assertNull(accountService.authenticate(userProfile));
        mockAccountRepository.save(userProfile);
        Assert.assertNotNull(accountService.authenticate(userProfile));

        UserProfile userProfile2 = new UserProfile("Chicken", "qwerty123");
        Assert.assertNull(accountService.authenticate(userProfile2));
        mockAccountRepository.save(userProfile2);
        Assert.assertNotNull(accountService.authenticate(userProfile2));
        Assert.assertNotNull(accountService.authenticate(userProfile));
    }

    @Test
    public void changePasswordTrueTest() {
        UserProfile userProfile = new UserProfile("fryChicken11", "qwerty11");
        mockAccountRepository.save(userProfile);
        String sessionId = accountService.authenticate(userProfile);
        mockSessionStorage.set(sessionId, String.valueOf(userProfile.getId()));
        accountService.changePassword(sessionId, "111");
        UserProfile u = mockAccountRepository.findByLogin(userProfile.getLogin());
        Assert.assertEquals("111", u.getPassword());
    }

    @Test
    public void changePasswordFalseTest() {
        Assert.assertThrows(SessionNotFoundException.class, () -> {
            accountService.changePassword("111", "111");
        });
    }
}
