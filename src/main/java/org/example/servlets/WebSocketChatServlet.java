package org.example.servlets;


import jakarta.servlet.annotation.WebServlet;
import org.eclipse.jetty.websocket.server.JettyWebSocketServlet;
import org.eclipse.jetty.websocket.server.JettyWebSocketServletFactory;
import org.example.services.ChatService;
import org.example.ChatWebSocket;

import java.time.Duration;


@WebServlet(name = "WebSocketChatServlet", urlPatterns = {"/chat"})
public class WebSocketChatServlet extends JettyWebSocketServlet {
    private final ChatService chatService;

    public WebSocketChatServlet() {
        this.chatService = new ChatService();
    }

    @Override
    protected void configure(JettyWebSocketServletFactory factory) {
        factory.setIdleTimeout(Duration.ofSeconds(5));
        factory.setCreator((req, resp) -> new ChatWebSocket(chatService));
    }
}
