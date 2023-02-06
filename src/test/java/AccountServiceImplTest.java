import mocks.MockAccountRepository;
import org.example.Context;
import org.example.UserProfile;
import org.example.database.AccountRepository;
import org.example.services.AccountService;
import org.example.services.AccountServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AccountServiceImplTest {
    AccountService accountService;
    MockAccountRepository mockAccountRepository = new MockAccountRepository();


    @Before
    public void init(){
        Context.getContext().put(AccountRepository.class, mockAccountRepository);
        accountService = new AccountServiceImpl();
    };

    @Test
    public void addNewUserTest()
    {
        UserProfile userProfile = new UserProfile("fryChicken", "qwerty");
        Assert.assertEquals(0, mockAccountRepository.countUsers());
        accountService.addNewUser(userProfile);
        Assert.assertEquals(1, mockAccountRepository.countUsers());

    }

    @Test
    public void findByLoginTest()
    {
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
