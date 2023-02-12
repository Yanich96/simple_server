package org.example;

import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.server.config.JettyWebSocketServletContainerInitializer;
import org.example.servlets.SignInServlet;
import org.example.servlets.SignUpServlet;
import org.example.servlets.WebSocketChatServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JettyConfiguration {
    private static final Logger logger = LogManager.getLogger();
    private Server server = null;

    @Autowired
    public JettyConfiguration(SignUpServlet signUpServlet, SignInServlet signInServlet) throws Exception {
        this.server = new Server(8080);
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        JettyWebSocketServletContainerInitializer.configure(servletContextHandler, null);
        servletContextHandler.addServlet(new ServletHolder(signUpServlet), "/signup");
        servletContextHandler.addServlet(new ServletHolder(signInServlet), "/signin");
        servletContextHandler.addServlet(new ServletHolder(new WebSocketChatServlet()), "/chat");
        server.setHandler(servletContextHandler);
        server.start();
        logger.info("Server started");
    }

    public void join() throws InterruptedException {
        this.server.join();
    }
}
