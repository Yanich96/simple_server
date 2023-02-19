package org.example.servlets;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.UserProfile;
import org.example.exceptions.LoginConflictException;
import org.example.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SignUpServlet extends HttpServlet {

    @Autowired
    private AccountService accountService;



    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        UserProfile user = new UserProfile(login, password);
        try {
            accountService.signUpNewUser(user);
        }
        catch (LoginConflictException lce)
        {
            resp.getWriter().print("Try another login or password");
            resp.setStatus(409);
        }

    }
}
