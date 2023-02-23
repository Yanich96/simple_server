package org.example.servlets;


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
public class ChangePasswordServlet extends BaseServlet {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private AccountService accountService;

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("Starting to change password...");
        try {
            logger.info("Getting sessionId and new password");
            String sessionId = req.getHeader("sessionId");
            String newPassword = req.getHeader("password");
            logger.info("SessionId and new password were gotten");
            accountService.changePassword(sessionId, newPassword);
            logger.info("Password was changed successfully");
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
        return "/changePassword";
    }
}
