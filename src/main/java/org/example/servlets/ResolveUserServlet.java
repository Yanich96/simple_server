package org.example.servlets;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.exceptions.SessionNotFoundException;
import org.example.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ResolveUserServlet extends BaseServlet {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private AccountService accountService;
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("Starting to get id ...");
        try {
            logger.info("Getting sessionId");
            String sessionId = req.getHeader("sessionId");
            logger.info("SessionId was gotten");
            long idUser = accountService.getId(sessionId);
            logger.info("Password was changed successfully");
            resp.getWriter().print(idUser);
        }
        catch (SessionNotFoundException e)
        {
            logger.info("Password was not changed");
            logger.error(e);
            resp.setStatus(401);
        }
    }

    @Override
    public String uri() {
        return "/resolveUser";
    }
}
