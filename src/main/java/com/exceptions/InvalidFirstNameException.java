package com.exceptions;

public class InvalidFirstNameException extends Exception {
    public InvalidFirstNameException() {
        super("First name is invalid");
    }
}
