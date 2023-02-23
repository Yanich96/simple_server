package org.example;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.server.config.JettyWebSocketServletContainerInitializer;
import org.example.servlets.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JettyConfiguration {
    private static final Logger logger = LogManager.getLogger();
    private Server server = null;

    @Autowired
    public JettyConfiguration(List<BaseServlet> servlets) throws Exception {
        this.server = new Server(8080);
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        JettyWebSocketServletContainerInitializer.configure(servletContextHandler, null);
        for (BaseServlet servlet : servlets) {
            servletContextHandler.addServlet(new ServletHolder(servlet), servlet.uri());
        }
        server.setHandler(servletContextHandler);
        server.start();
        logger.info("Server started");
    }

    public void join() throws InterruptedException {
        this.server.join();
    }
}
