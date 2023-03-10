package org.example.test;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.SessionFactoryConfiguration;
import org.example.UserProfile;
import org.example.test.mocks.MockAccountRepository;
import org.example.services.AccountServiceImpl;
import org.example.servlets.SignUpServlet;
import org.example.test.mocks.MockSessionStorage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Mockito.*;

@ContextConfiguration(
        classes = {
                MockAccountRepository.class,
                AccountServiceImpl.class,
                SignUpServlet.class,
                SessionFactoryConfiguration.class,
                MockSessionStorage.class
        }
)
@RunWith(SpringJUnit4ClassRunner.class)
public class SignUpServletTest {
    @Autowired
    MockAccountRepository mockAccountRepository;
    @Autowired
    SignUpServlet servlet;
    @Autowired
    MockSessionStorage mockSessionStorage;

    @Before
    public void init() {
        mockAccountRepository.reset();
        UserProfile user = new UserProfile("Loki", "rtyu2345");
        mockAccountRepository.save(user);
    }

    @Test
    public void doPostTest() throws Exception {
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = null;
        when(request.getParameter("login")).thenReturn("Lok");
        when(request.getParameter("password")).thenReturn("rtyu2345");
        servlet.doPost(request, response);
        verify(request, atLeastOnce()).getParameter("login");
        verify(request, atLeastOnce()).getParameter("password");
    }

    @Test
    public void doPostTestEqualsLogin() throws IOException {
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("login")).thenReturn("Loki");
        when(request.getParameter("password")).thenReturn("rtyu2345");
        ByteArrayOutputStream line = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(line);
        when(response.getWriter()).thenReturn(writer);
        servlet.doPost(request, response);
        verify(response, times(1)).setStatus(409);
    }
}
