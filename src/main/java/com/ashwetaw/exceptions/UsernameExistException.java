package com.ashwetaw.exceptions;

public class UsernameExistException extends SpringJWTException {
    public UsernameExistException(String s) {
        super(s);
    }
}
