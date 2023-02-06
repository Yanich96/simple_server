package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.server.config.JettyWebSocketServletContainerInitializer;
import org.example.database.AccountRepository;
import org.example.database.AccountRepositoryImpl;
import org.example.database.Database;
import org.example.database.DatabaseRelationImpl;
import org.example.services.AccountService;
import org.example.services.AccountServiceImpl;
import org.example.servlets.SignInServlet;
import org.example.servlets.SignUpServlet;
import org.example.servlets.WebSocketChatServlet;

public class Main {
    private static final Logger logger = LogManager.getLogger();
    public static void main(String[] args) throws Exception{
        var configuration = new DatabaseRelationImpl.Configuration(
                "org.h2.Driver",
                "jdbc:h2:./h2db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
                "test",
                "test"
        );
        Context.getContext().put(Database.class, new DatabaseRelationImpl(configuration));
        Context.getContext().put(AccountRepository.class, new AccountRepositoryImpl());
        Context.getContext().put(AccountService.class, new AccountServiceImpl());

        Server server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        JettyWebSocketServletContainerInitializer.configure(context, null);
        context.addServlet(new ServletHolder(new SignUpServlet()), "/signup");
        context.addServlet(new ServletHolder(new SignInServlet()), "/signin");
        context.addServlet(new ServletHolder(new WebSocketChatServlet()), "/chat");
        server.setHandler(context);

        server.start();
        logger.info("Server started");
        System.out.println("Server started");
        server.join();
    }
}