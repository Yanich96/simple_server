package org.example.exceptions;

public class ConflictUniqueNameException extends RuntimeException{
    public ConflictUniqueNameException(String str) {
        super("Table must have the column " + str + " with unique values");
    }
}
