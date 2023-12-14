package com.exceptions;

public class InvalidLastNameException extends Exception {
    public InvalidLastNameException() {
        super("Last name is invalid");
    }
}