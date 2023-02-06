import org.example.Context;
import org.example.UserProfile;
import org.example.database.AccountRepository;
import org.example.database.AccountRepositoryImpl;
import org.example.database.Database;
import org.example.database.DatabaseRelationImpl;
import org.example.services.AccountServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AccountRepositoryImplTest {


    AccountRepositoryImpl accountRepository = null;
    @Before
    public void init(){
        var configuration = new DatabaseRelationImpl.Configuration(
                "org.h2.Driver",
                "jdbc:h2:./h2db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
                "test",
                "test"
        );
        Database db = new DatabaseRelationImpl(configuration);
        Context.getContext().put(Database.class, db);
        accountRepository = new AccountRepositoryImpl();
    };

    @Test
    public void saveTest()
    {

        UserProfile user = new UserProfile("Cat", "0809");
        Assert.assertNull(accountRepository.findByLogin(user.getLogin()));
        accountRepository.save(user);
        Assert.assertTrue(user.equals(accountRepository.findByLogin(user.getLogin())));
    }
}
