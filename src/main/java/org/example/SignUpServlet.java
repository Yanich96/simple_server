package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

public class SignUpServlet extends HttpServlet {
    private final Database database;


    SignUpServlet(Database db)
    {
        this.database = db;
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)  {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        UserProfile user = new UserProfile(login, password);
        try {
            database.insertToDB(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
