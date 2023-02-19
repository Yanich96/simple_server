package org.example.exceptions;

public class LoginConflictException extends MetaServerException{
    public LoginConflictException(String login) {
        super("User with login " + login + " already exists");
    }
}
