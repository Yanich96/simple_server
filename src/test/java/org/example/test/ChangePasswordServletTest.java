package org.example.test;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.SessionFactoryConfiguration;
import org.example.UserProfile;
import org.example.services.AccountService;
import org.example.services.AccountServiceImpl;
import org.example.servlets.ChangePasswordServlet;
import org.example.test.mocks.MockAccountRepository;
import org.example.test.mocks.MockSessionStorage;
import org.junit.Assert;
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
                ChangePasswordServlet.class,
                SessionFactoryConfiguration.class,
                MockSessionStorage.class
        }
)
@RunWith(SpringJUnit4ClassRunner.class)
public class ChangePasswordServletTest {
    @Autowired
    MockAccountRepository mockAccountRepository;
    @Autowired
    ChangePasswordServlet servlet;
    @Autowired
    AccountService accountService;

    @Before
    public void init() {
        mockAccountRepository.reset();
    }

    @Test
    public void changePwdTest() throws IOException {
        UserProfile user = new UserProfile("Loki", "rtyu2345");
        mockAccountRepository.save(user);
        String session = accountService.authenticate(user);
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getHeader("sessionId")).thenReturn(session);
        when(request.getHeader("password")).thenReturn("666666");
        ByteArrayOutputStream line = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(line);
        when(response.getWriter()).thenReturn(writer);
        servlet.doPost(request, response);
        UserProfile u = mockAccountRepository.findByLogin("Loki");
        Assert.assertEquals("666666", u.getPassword());
    }

    @Test
    public void changePwdFailedTest() throws IOException {
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getHeader("sessionId")).thenReturn("111");
        when(request.getHeader("password")).thenReturn("666666");
        ByteArrayOutputStream line = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(line);
        when(response.getWriter()).thenReturn(writer);
        servlet.doPost(request, response);
        verify(response, times(1)).setStatus(401);
    }
}
