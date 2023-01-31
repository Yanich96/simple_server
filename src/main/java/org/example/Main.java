package org.example;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    public static void main(String[] args) throws Exception{
        Context.getContext().put(Database.class, new DatabaseRelationImpl());
        Context.getContext().put(AccountRepository.class, new AccountRepository());
        Context.getContext().put(AccountService.class, new AccountServiceImpl());

        Server server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(Frontend.class, "/mirror");
        context.addServlet(new ServletHolder(new SignUpServlet()), "/signup");
        context.addServlet(new ServletHolder(new SignInServlet()), "/signin");
        server.setHandler(context);

        server.start();
        System.out.println("Server started");
        server.join();
    }
}