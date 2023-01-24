package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class SignInServlet extends HttpServlet {
    private final AccountService accountService;

    SignInServlet(AccountService accountService)
    {
        this.accountService = accountService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        UserProfile user = new UserProfile(login, password);
        boolean registered = accountService.findUser(user);
        if(registered)
            resp.getWriter().print("Authorized: "+login);
        else
        {
            resp.getWriter().print("Unauthorized");
            resp.setStatus(401);
        }


    }


}
