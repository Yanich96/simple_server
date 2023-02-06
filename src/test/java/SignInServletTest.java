import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mocks.MockAccountRepository;
import org.example.Context;
import org.example.UserProfile;
import org.example.database.AccountRepository;
import org.example.services.AccountService;
import org.example.services.AccountServiceImpl;
import org.example.servlets.SignInServlet;
import org.example.servlets.SignUpServlet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atLeastOnce;

public class SignInServletTest {
    MockAccountRepository mockAccountRepository = new MockAccountRepository();

    @Before
    public void init(){
        Context.getContext().put(AccountRepository.class, mockAccountRepository);
        UserProfile user = new UserProfile("Loki","rtyu2345");
        mockAccountRepository.save(user);
        AccountService accountService = new AccountServiceImpl();
        Context.getContext().put(AccountService.class, accountService);
    };

    @Test
    public void doPostAuthorizedTest() throws Exception {
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        String login = "Loki";
        when(request.getParameter("login")).thenReturn("Loki");
        when(request.getParameter("password")).thenReturn("rtyu2345");
        SignInServlet servlet = new SignInServlet();
        ByteArrayOutputStream line = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(line);
        when(response.getWriter()).thenReturn(writer);
        servlet.doPost(request, response);
        Assert.assertEquals("Authorized: "+login, line.toString());
    }


    @Test
    public void doPostUnauthorizedTest() throws Exception {
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("login")).thenReturn("Love");
        when(request.getParameter("password")).thenReturn("sv1402");
        SignInServlet servlet = new SignInServlet();
        ByteArrayOutputStream line = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(line);
        when(response.getWriter()).thenReturn(writer);
        servlet.doPost(request, response);
        Assert.assertEquals("Unauthorized", line.toString());
    }
}
