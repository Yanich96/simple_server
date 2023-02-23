package org.example.servlets;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.UserProfile;
import org.example.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class SignInServlet extends BaseServlet {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    AccountService accountService;

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        UserProfile user = new UserProfile(login, password);
        String sessionId = accountService.authenticate(user);
        if (sessionId != null) {
            logger.info("User " + login + " authorized");
            resp.getWriter().println("SessionId: " + sessionId);
            resp.getWriter().print("Authorized: " + login);
        } else {
            logger.info("User " + login + " unauthorized");
            resp.getWriter().print("Unauthorized");
            resp.setStatus(401);
        }
        resp.getWriter().flush();
    }

    @Override
    public String uri() {
        return "/signin";
    }
}
