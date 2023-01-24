package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class SignUpServlet extends HttpServlet {
    private final AccountService accountService;

    SignUpServlet(AccountService accountService)
    {
        this.accountService = accountService;
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)  {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        UserProfile user = new UserProfile(login, password);
        accountService.addNewUser(user);
    }

}
