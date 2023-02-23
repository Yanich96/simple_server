package org.example.servlets;

import jakarta.servlet.http.HttpServlet;

public abstract class BaseServlet extends HttpServlet {
    public abstract String uri();
}
