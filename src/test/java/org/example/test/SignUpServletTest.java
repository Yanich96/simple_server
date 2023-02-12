package org.example.test;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.test.mocks.MockAccountRepository;
import org.example.services.AccountServiceImpl;
import org.example.servlets.SignUpServlet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.*;

@ContextConfiguration(
        classes = {
                MockAccountRepository.class,
                AccountServiceImpl.class,
                SignUpServlet.class
        }
)
@RunWith(SpringJUnit4ClassRunner.class)
public class SignUpServletTest {
  @Autowired
  MockAccountRepository mockAccountRepository;
  @Autowired
  SignUpServlet servlet;


  @Test
  public void doPostTest() throws Exception {
    final  HttpServletRequest request = mock(HttpServletRequest.class);
    final HttpServletResponse response = null;
    when(request.getParameter("login")).thenReturn("Loki");
    when(request.getParameter("password")).thenReturn("rtyu2345");
    servlet.doPost(request, response);
    verify(request, atLeastOnce()).getParameter("login");
    verify(request, atLeastOnce()).getParameter("password");
  }
}
