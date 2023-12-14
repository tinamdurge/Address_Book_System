package com.exceptions;

public class InvalidPhoneNumberException extends Exception {
    public InvalidPhoneNumberException() {
        super("Phone number is invalid");
    }
}
