package com.exceptions;

public class InvalidEmailException extends Exception {
    public InvalidEmailException() {
        super("Email is invalid");
    }
}
