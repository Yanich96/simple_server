package org.example.test;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.SessionFactoryConfiguration;
import org.example.UserProfile;
import org.example.services.AccountServiceImpl;
import org.example.servlets.SignInServlet;
import org.example.test.mocks.MockAccountRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@ContextConfiguration(
        classes = {
                MockAccountRepository.class,
                AccountServiceImpl.class,
                SignInServlet.class,
                SessionFactoryConfiguration.class
        }
)
@RunWith(SpringJUnit4ClassRunner.class)
public class SignInServletTest {
    @Autowired
    MockAccountRepository mockAccountRepository;
    @Autowired
    SignInServlet servlet;

    @Before
    public void init(){
        UserProfile user = new UserProfile("Loki","rtyu2345");
        mockAccountRepository.save(user);
    };

    @Test
    public void doPostAuthorizedTest() throws Exception {
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        String login = "Loki";
        when(request.getParameter("login")).thenReturn("Loki");
        when(request.getParameter("password")).thenReturn("rtyu2345");
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
        ByteArrayOutputStream line = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(line);
        when(response.getWriter()).thenReturn(writer);
        servlet.doPost(request, response);
        Assert.assertEquals("Unauthorized", line.toString());
    }
}
