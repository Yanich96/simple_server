package org.example.exceptions;

public abstract class MetaServerException extends RuntimeException {
    public MetaServerException(String message) {
        super(message);
    }
}
