package com.ashwetaw.exceptions;

public class EmailNotFoundException extends SpringJWTException {
    public EmailNotFoundException(String s) {
        super(s);
    }
}
