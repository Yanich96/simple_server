package org.example.servlets;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Context;
import org.example.UserProfile;
import org.example.services.AccountService;

public class SignUpServlet extends HttpServlet {

    private final AccountService accountService;


    public SignUpServlet() {
        this.accountService = Context.getContext().get(AccountService.class);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        UserProfile user = new UserProfile(login, password);
        accountService.addNewUser(user);
    }
}
