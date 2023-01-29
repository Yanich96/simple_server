package org.example;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    public static void main(String[] args) throws Exception{
        Database db = new Database();
        Server server = new Server(8080);
        AccountService accountService = new AccountService();
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(Frontend.class, "/mirror");
        context.addServlet(new ServletHolder(new SignUpServlet(db)), "/signup");
        context.addServlet(new ServletHolder(new SignInServlet(db)), "/signin");
        server.setHandler(context);

        server.start();
        System.out.println("Server started");
        server.join();
    }
}