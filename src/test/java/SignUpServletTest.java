import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mocks.MockAccountRepository;
import org.example.Context;
import org.example.database.AccountRepository;
import org.example.services.AccountService;
import org.example.services.AccountServiceImpl;
import org.example.servlets.SignUpServlet;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class SignUpServletTest {
  MockAccountRepository mockAccountRepository = new MockAccountRepository();

  @Before
  public void init(){
    Context.getContext().put(AccountRepository.class, mockAccountRepository);
    AccountService accountService = new AccountServiceImpl();
    Context.getContext().put(AccountService.class, accountService);
  };

  @Test
  public void doPostTest() throws Exception {
    final  HttpServletRequest request = mock(HttpServletRequest.class);
    final HttpServletResponse response = null;
    when(request.getParameter("login")).thenReturn("Loki");
    when(request.getParameter("password")).thenReturn("rtyu2345");

    SignUpServlet servlet = new SignUpServlet();
    servlet.doPost(request, response);
    verify(request, atLeastOnce()).getParameter("login");
    verify(request, atLeastOnce()).getParameter("password");

  }
}
