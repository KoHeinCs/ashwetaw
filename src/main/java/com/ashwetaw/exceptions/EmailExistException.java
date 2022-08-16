package com.ashwetaw.exceptions;

public class EmailExistException extends SpringJWTException {
    public EmailExistException(String s) {
        super(s);
    }
}
