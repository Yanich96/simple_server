package org.example.exceptions;

public class SessionNotFoundException extends MetaServerException {
    public SessionNotFoundException(String message) {
        super(message);
    }
}
