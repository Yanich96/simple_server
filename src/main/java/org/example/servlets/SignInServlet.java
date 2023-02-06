package org.example.servlets;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Context;
import org.example.UserProfile;
import org.example.services.AccountService;

import java.io.IOException;

public class SignInServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();
    private final AccountService accountService;


    public SignInServlet()
    {
        this.accountService = Context.getContext().get(AccountService.class);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        UserProfile user = new UserProfile(login, password);
        boolean registered = accountService.authenticate(user);
        if(registered) {
            logger.info("User " + login + " authorized");
            resp.getWriter().print("Authorized: " + login);
        }
        else
        {
            logger.info("User "+login+" unauthorized");
            resp.getWriter().print("Unauthorized");
            resp.setStatus(401);
        }
        resp.getWriter().flush();
    }
}
